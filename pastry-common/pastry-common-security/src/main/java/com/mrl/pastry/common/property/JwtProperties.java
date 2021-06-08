package com.mrl.pastry.common.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Jwt token properties
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/19
 */
@ConfigurationProperties
public class JwtProperties {

    @Value("${security.jwt.secret:'@MrL.Pastry'}")
    private String secret;

    /**
     * days
     */
    @Value("${security.jwt.expiration:30}")
    private Long expiration;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}
