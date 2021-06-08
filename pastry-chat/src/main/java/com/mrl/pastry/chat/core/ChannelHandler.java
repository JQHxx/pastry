package com.mrl.pastry.chat.core;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mrl.pastry.chat.handler.EventStrategy;
import com.mrl.pastry.chat.utils.ApplicationContextUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * Channel handler
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/31
 */
@Slf4j
public class ChannelHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static final ChannelGroup CLIENTS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private final EventStrategy eventStrategy = ApplicationContextUtils.getBean(EventStrategy.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        Channel channel = channelHandlerContext.channel();
        JSONObject object = JSONUtil.parseObj(textWebSocketFrame.text());
        eventStrategy.handle(channel, object);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        CLIENTS.add(ctx.channel());
        log.debug("new channel: [{}] is added to CLIENTS channel group", ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        CLIENTS.remove(ctx.channel());
        log.debug("channel: [{}] is removed from CLIENTS channel group", ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.channel().close();
        CLIENTS.remove(ctx.channel());
        log.debug("channel: [{}] is closed by exception:[{}] and removed from CLIENTS channel group",
                ctx.channel().id().asLongText(), cause.getMessage());
    }
}
