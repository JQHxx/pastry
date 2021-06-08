package com.mrl.pastry.portal.constant;

/**
 * Pastry system constant
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/9
 */
public interface DefaultConstant {

    String PROTOCOL_HTTPS = "https://";

    String PROTOCOL_HTTP = "http://";

    /**
     * security
     */
    String USER_ROLE = "user";

    String ADMIN_ROLE = "admin";

    String USER_INITIAL_PASSWORD = "123456";

    /**
     * weixin
     */
    String CODE2_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    String GRANT_TYPE = "authorization_code";

    /**
     * es highlight
     */
    String PRE_TAG = "";
    String POST_TAG = "";
}
