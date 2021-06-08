package com.mrl.pastry.portal.config;

import cn.hutool.dfa.WordTree;
import com.mrl.pastry.portal.mapper.SensitiveWordMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Sensitive Configuration
 *
 * @author MrL
 * @version 1.0
 * @date 2021/6/1
 */
@Configuration
public class SensitiveConfiguration {

    private final SensitiveWordMapper sensitiveWordMapper;

    public SensitiveConfiguration(SensitiveWordMapper sensitiveMapper) {
        this.sensitiveWordMapper = sensitiveMapper;
    }

    @Bean
    public WordTree wordTree() {
        List<String> words = sensitiveWordMapper.getWords();
        WordTree wordTree = new WordTree();
        wordTree.addWords(words);
        return wordTree;
    }
}
