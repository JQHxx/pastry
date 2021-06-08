package com.mrl.pastry.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Url white-list properties
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/17
 */
@ConfigurationProperties(prefix = "security.ignore")
public class IgnoreProperties {

    private String[] postUrls = {};

    private String[] getUrls = {};

    public String[] getPostUrls() {
        return postUrls;
    }

    public void setPostUrls(String[] postUrls) {
        this.postUrls = postUrls;
    }

    public String[] getGetUrls() {
        return getUrls;
    }

    public void setGetUrls(String[] getUrls) {
        this.getUrls = getUrls;
    }
}
