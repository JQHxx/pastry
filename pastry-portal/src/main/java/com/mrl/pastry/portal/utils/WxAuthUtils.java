package com.mrl.pastry.portal.utils;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mrl.pastry.common.exception.ServiceException;
import com.mrl.pastry.portal.constant.DefaultConstant;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
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
@Component
public class WxAuthUtils {

    @Value("${wechat.app-id}")
    private String appId;

    @Value("${wechat.app-secret}")
    private String appSecret;

    /**
     * Get user's session_key & openid
     *
     * @param code js_code cannot be null
     * @return JSONObject{"session_key":"","openid":""}
     */
    public JSONObject getAuthInfo(@NonNull String code) {
        Assert.notNull(code, "code cannot be null");

        Map<String, Object> params = new HashMap<>(8);
        params.put("appid", this.appId);
        params.put("secret", this.appSecret);
        params.put("js_code", code);
        params.put("grant_type", DefaultConstant.GRANT_TYPE);

        String response = HttpUtil.get(DefaultConstant.CODE2_SESSION_URL, params);
        log.debug("get auth info: [{}]", response);
        return JSONUtil.parseObj(response);
    }

    /**
     * Decrypt user data from encryptedData
     *
     * @param sessionKey    session_key
     * @param encryptedData encryptedData
     * @param iv            iv
     * @return JSONObject{"nickName":"","gender":"","language":"","city":"","province":"","country":"","avatarUrl":":"}
     */
    public JSONObject getUserInfo(@NonNull String sessionKey, @NonNull String encryptedData, @NonNull String iv) {
        try {
            byte[] sessionKeyBytes = Base64Decoder.decode(sessionKey);
            byte[] ivBytes = Base64Decoder.decode(iv);
            byte[] encryptedDataBytes = Base64Decoder.decode(encryptedData);

            int base = 16;
            if (sessionKeyBytes.length % base != 0) {
                int groups = sessionKeyBytes.length / base + 1;
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(sessionKeyBytes, 0, temp, 0, sessionKeyBytes.length);
                sessionKeyBytes = temp;
            }
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec sessionKeySpec = new SecretKeySpec(sessionKeyBytes, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivBytes));
            cipher.init(Cipher.DECRYPT_MODE, sessionKeySpec, parameters);

            byte[] userData = cipher.doFinal(encryptedDataBytes);
            JSONObject jsonObject = JSONUtil.parseObj(new String(userData));
            log.debug("get user data:[{}]", jsonObject);
            return jsonObject;
        } catch (Exception e) {
            throw new ServiceException("解析用户数据失败", e);
        }
    }
}
