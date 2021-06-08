package com.mrl.pastry.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.pastry.portal.model.entity.SensitiveWord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Sensitive Mapper
 *
 * @author MrL
 * @version 1.0
 * @date 2021/6/1
 */
public interface SensitiveWordMapper extends BaseMapper<SensitiveWord> {

    /**
     * Gets sensitive words
     *
     * @return all words
     */
    @Select("select word from sensitive_word")
    List<String> getWords();
}
