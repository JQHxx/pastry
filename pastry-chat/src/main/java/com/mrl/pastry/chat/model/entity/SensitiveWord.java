package com.mrl.pastry.chat.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SensitiveWord entity
 *
 * @author MrL
 * @version 1.0
 * @date 2021/6/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SensitiveWord {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String word;
}
