package com.mrl.pastry.portal.feign;

import com.mrl.pastry.common.utils.ServletUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * Feign Configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/6/7
 */
@Component
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String header = HttpHeaders.AUTHORIZATION;
        String token = ServletUtils.getHeaderIgnoreCase(header);
        if (token != null) {
            // 服务间调用传递jwt-token
            template.header(header, token);
        }
    }
}
