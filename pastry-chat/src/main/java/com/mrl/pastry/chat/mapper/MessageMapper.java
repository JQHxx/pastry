package com.mrl.pastry.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.pastry.chat.model.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Message mapper
 *
 * @author MrL
 * @since 2021-04-05
 */
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * Batch update to signed status
     *
     * @param ids    unsigned message ids
     * @param userId receiverId
     */
    void updateBatchStatus(@Param("ids") List<Long> ids, @Param("userId") Long userId);
}
