package com.mrl.pastry.portal.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.WordTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 敏感词过滤
 *
 * @author MrL
 * @version 1.0
 * @date 2021/6/1
 */
@Slf4j
@Component
public class SensitiveUtils {

    private final WordTree wordTree;

    public SensitiveUtils(WordTree wordTree) {
        this.wordTree = wordTree;
    }

    public String refine(@NonNull String content) {
        Assert.notNull(content, "content must not be blank");
        // gets all sensitive words
        List<String> words = wordTree.matchAll(content);
        for (String word : words) {
            // replace sensitive word with mask
            String mask = StrUtil.repeat("*", word.length());
            content = StrUtil.replace(content, word, mask);
        }
        return content;
    }
}
