package com.mrl.pastry.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.common.exception.BadRequestException;
import com.mrl.pastry.common.utils.PageUtils;
import com.mrl.pastry.common.utils.SecurityUtils;
import com.mrl.pastry.portal.event.user.UserFollowEvent;
import com.mrl.pastry.portal.mapper.FollowMapper;
import com.mrl.pastry.portal.mapper.UserMapper;
import com.mrl.pastry.portal.model.dto.UserDetailDTO;
import com.mrl.pastry.portal.model.entity.Follow;
import com.mrl.pastry.portal.model.entity.User;
import com.mrl.pastry.portal.model.vo.FollowVO;
import com.mrl.pastry.portal.service.FollowService;
import com.mrl.pastry.portal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Follow service implementation
 *
 * @author MrL
 * @since 2021-03-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    private final ApplicationEventPublisher eventPublisher;

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public Boolean follow(@NonNull Long bloggerId) {
        userService.getOneUserByQueryWrapper(Wrappers.<User>lambdaQuery().eq(User::getId, bloggerId).select(User::getId))
                .orElseThrow(() -> new BadRequestException("用户不存在!"));

        Long userId = SecurityUtils.getCurrentUserId();
        if (bloggerId.equals(userId)) {
            throw new BadRequestException("请勿关注自己!");
        }
        // 优化: 唯一索引(userId,followId)
        Follow exist = getOne(Wrappers.<Follow>lambdaQuery().eq(Follow::getUserId, userId).eq(Follow::getFollowId, bloggerId)
                .select(Follow::getId, Follow::getStatus));
        boolean follow = true;
        if (Objects.isNull(exist)) {
            try {
                // 不存在记录 -> +关注
                Follow record = new Follow(userId, bloggerId);
                save(record);
                log.debug("Saved a follow record: [{}]", record);
            } catch (DuplicateKeyException e) {
                throw new BadRequestException("请勿重复关注!");
            }
        } else {
            // 存在记录 -> 更新为相反的状态
            follow = !exist.getStatus();
            update(Wrappers.<Follow>lambdaUpdate().eq(Follow::getId, exist.getId()).set(Follow::getStatus, follow));
        }
        // publish user-follow event
        eventPublisher.publishEvent(new UserFollowEvent(this, userId, bloggerId, follow));
        return follow;
    }

    @Override
    public Boolean isFollow(@NonNull Long userId, @NonNull Long bloggerId) {
        Follow exist = getOne(Wrappers.<Follow>lambdaQuery().eq(Follow::getUserId, userId).eq(Follow::getFollowId, bloggerId)
                .select(Follow::getStatus));
        return !Objects.isNull(exist) && exist.getStatus();
    }

    @Override
    public IPage<FollowVO> getPage(Pageable pageable, @NonNull Long bloggerId, Boolean fans) {
        Page<Follow> page = PageUtils.convertToPage(pageable);
        Long userId = SecurityUtils.getCurrentUserId();
        LambdaQueryWrapper<Follow> queryWrapper = Wrappers.<Follow>lambdaQuery().eq(Follow::getStatus, true);
        if (fans) {
            // 优化：索引(follow_id)
            return page(page, queryWrapper.eq(Follow::getFollowId, bloggerId).select(Follow::getUserId)).convert(f -> {
                UserDetailDTO userDetailDTO = userService.getUserDetailInfo(f.getUserId());
                return new FollowVO(userDetailDTO, isFollow(userId, f.getUserId()));
            });
        } else {
            // 这里走唯一索引(userId,followId)
            return page(page, queryWrapper.eq(Follow::getUserId, bloggerId).select(Follow::getFollowId)).convert(f -> {
                UserDetailDTO userDetailDTO = userService.getUserDetailInfo(f.getFollowId());
                return new FollowVO(userDetailDTO, isFollow(userId, f.getFollowId()));
            });
        }
    }
}
