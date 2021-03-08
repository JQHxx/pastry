package com.mrl.pastry.portal.utils;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mrl.pastry.portal.constant.WxAppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Weixin auth utilities
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/2
 */
@Slf4j
public class WxAuthUtils {

    private WxAuthUtils() {
    }

    /**
     * Get user's session_key & openid
     *
     * @param code js_code cannot be null
     * @return JSONObject{"session_key":"","openid":""}
     */
    public static JSONObject getAuthInfo(@NonNull String code) {
        Assert.notNull(code, "code cannot be null");

        Map<String, Object> params = new HashMap<>(8);
        params.put("appid", WxAppConstant.APP_ID);
        params.put("secret", WxAppConstant.APP_SECRET);
        params.put("js_code", code);
        params.put("grant_type", WxAppConstant.GRANT_TYPE);

        String response = HttpUtil.get(WxAppConstant.CODE2_SESSION_URL, params);
        return JSONUtil.parseObj(response);
    }

    /**
     * Decrypt user data from encryptedData
     *
     * @param sessionKey    session_key
     * @param encryptedData encryptedData
     * @param iv            iv
     * @return JSONObject{"nickName":"","gender":,"language":"","city":"","province":"","country":"","avatarUrl":":"}
     */
    public static JSONObject getUserInfo(@NonNull String sessionKey, @NonNull String encryptedData, @NonNull String iv) {
        try {
            SecretKeySpec sessionKeySpec = new SecretKeySpec(Base64Decoder.decode(sessionKey), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(Base64Decoder.decode(iv));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, sessionKeySpec, ivSpec);
            byte[] userData = cipher.doFinal(Base64Decoder.decode(encryptedData));
            return JSONUtil.parseObj(new String(userData));
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
