package com.mrl.pastry.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.pastry.portal.model.entity.UserLike;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserLike service interface
 *
 * @author MrL
 * @since 2021-04-01
 */
public interface UserLikeService extends IService<UserLike> {

    /**
     * Checks whether user gave a like
     *
     * @param userId must not be null
     * @param blogId must not be null
     * @return 1:like
     */
    Boolean isLike(@NonNull Long userId, @NonNull Long blogId);

    /**
     * Save or update a like record
     *
     * @param userLike must not be null
     */
    @Transactional(rollbackFor = Exception.class)
    void saveRecord(@NonNull UserLike userLike);

    /**
     * Cancel the like
     *
     * @param blogId must not be null
     * @param userId must not be null
     */
    @Transactional(rollbackFor = Exception.class)
    void cancelLike(@NonNull Long blogId, @NonNull Long userId);
}
