package com.mrl.pastry.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.pastry.portal.model.entity.Follow;
import com.mrl.pastry.portal.model.vo.FollowVO;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

/**
 * Follow service interface
 *
 * @author MrL
 * @since 2021-03-07
 */
public interface FollowService extends IService<Follow> {

    /**
     * Follow or unfollow the blogger
     *
     * @param bloggerId must not be null
     * @return true:done
     */
    @Transactional(rollbackFor = Exception.class)
    Boolean follow(@NonNull Long bloggerId);

    /**
     * Checks whether the user follows the blogger
     *
     * @param userId    must not be null
     * @param bloggerId must not be null
     * @return true: follow
     */
    Boolean isFollow(@NonNull Long userId, @NonNull Long bloggerId);

    /**
     * Get a page of fans or followVOs
     *
     * @param pageable  pagination information.
     * @param bloggerId must not be null
     * @param fans      true:fans; false:follow
     * @return a page of FollowVOs
     */
    IPage<FollowVO> getPage(Pageable pageable, @NonNull Long bloggerId, Boolean fans);
}
