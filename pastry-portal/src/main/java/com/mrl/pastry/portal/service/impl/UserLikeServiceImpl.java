package com.mrl.pastry.portal.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.common.exception.ServiceException;
import com.mrl.pastry.portal.mapper.UserLikeMapper;
import com.mrl.pastry.portal.model.entity.UserLike;
import com.mrl.pastry.portal.model.enums.TargetType;
import com.mrl.pastry.portal.service.UserLikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * UserLike service implementation
 *
 * @author MrL
 * @since 2021-04-01
 */
@Slf4j
@Service
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike> implements UserLikeService {

    @Override
    public Boolean isLike(@NonNull Long userId, @NonNull Long blogId) {
        // 优化: 索引(userId、targetId)
        UserLike like = getOne(Wrappers.<UserLike>lambdaQuery().eq(UserLike::getUserId, userId).eq(UserLike::getTargetId, blogId)
                .eq(UserLike::getTargetType, TargetType.BLOG).select(UserLike::getStatus));
        return !Objects.isNull(like) && like.getStatus();
    }

    @Override
    public void saveRecord(@NonNull UserLike userLike) {
        UserLike record = getOne(Wrappers.<UserLike>lambdaQuery().eq(UserLike::getUserId, userLike.getUserId()).eq(UserLike::getTargetId, userLike.getTargetId())
                .eq(UserLike::getTargetType, TargetType.BLOG).select(UserLike::getId, UserLike::getStatus));
        if (Objects.isNull(record)) {
            save(userLike);
            log.debug("Saved a userLike record: [{}]", userLike);
        } else {
            if (!userLike.getStatus().equals(record.getStatus())) {
                update(Wrappers.<UserLike>lambdaUpdate().eq(UserLike::getId, record.getId()).set(UserLike::getStatus, userLike.getStatus()));
            }
        }
    }

    @Override
    public void cancelLike(@NonNull Long blogId, @NonNull Long userId) {
        boolean update = update(Wrappers.<UserLike>lambdaUpdate().eq(UserLike::getUserId, userId).eq(UserLike::getTargetId, blogId)
                .eq(UserLike::getTargetType, TargetType.BLOG).set(UserLike::getStatus, false));
        if (!update) {
            throw new ServiceException("取消点赞失败");
        }
    }
}
