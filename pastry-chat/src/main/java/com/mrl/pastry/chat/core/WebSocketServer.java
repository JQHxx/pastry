package com.mrl.pastry.chat.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * WebSocket server
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/31
 */
@Slf4j
@Component
public class WebSocketServer implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${server.port}")
    private int port;

    public void run(int port) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            // 将请求与应答消息编码或解码为http消息
                            pipeline.addLast("http-codec", new HttpServerCodec());

                            // 将http消息的多个部分组合为一个完整的Http消息 1024*64
                            pipeline.addLast("aggregator", new HttpObjectAggregator(65536));

                            // 用来向客户端发送html5文件，主要用于支持浏览器与服务端进行websocket通信
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());

                            // 通过该类直接管理握手(close、ping、pong)、心跳等操作
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

                            // 客户端如果在一段时间内没有与服务端维持心跳, 则主动断开连接
                            pipeline.addLast(new IdleStateHandler(30, 0, 0));
                            // 心跳检测handler
                            pipeline.addLast(new HeartBeatHandler());

                            // 解析并处理WebSocketFrame
                            pipeline.addLast(new ChannelHandler());
                        }
                    });
            // 绑定端口
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            log.info("netty server[port:{}] is running...", port);
            this.run(port);
        }
    }
}
