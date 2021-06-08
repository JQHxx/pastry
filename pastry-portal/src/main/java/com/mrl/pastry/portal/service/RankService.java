package com.mrl.pastry.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrl.pastry.portal.model.vo.BlogVO;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Rank service interface
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/1
 */
public interface RankService {

    long RANGE = 10;

    /**
     * Gets rank list
     *
     * @return list of blogs
     */
    IPage<BlogVO> getRankList();

    /**
     * Adds blogId to the rank list
     *
     * @param blogId must not be null
     * @param delta  must not be null
     */
    void offer(@NonNull Long blogId, @NonNull Integer delta);

    /**
     * Remove blogId from the rank list
     *
     * @param blogId must not be null
     */
    void remove(@NonNull Long blogId);
}
