package com.mrl.pastry.portal.security.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Jwt token
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
@Data
public class JwtToken {

    /**
     * Authorization type
     */
    @JsonIgnore
    private String header = "Bearer";

    /**
     * Access token
     */
    @JsonProperty("access_token")
    private String accessToken;
}
