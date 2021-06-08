package com.mrl.pastry.portal.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.common.exception.BadRequestException;
import com.mrl.pastry.common.utils.BeanUtils;
import com.mrl.pastry.common.utils.JwtUtils;
import com.mrl.pastry.common.utils.SecurityUtils;
import com.mrl.pastry.portal.constant.DefaultConstant;
import com.mrl.pastry.portal.constant.RedisConstant;
import com.mrl.pastry.portal.event.user.UserNewEvent;
import com.mrl.pastry.portal.mapper.UserMapper;
import com.mrl.pastry.portal.model.dto.UserBaseDTO;
import com.mrl.pastry.portal.model.dto.UserDetailDTO;
import com.mrl.pastry.portal.model.entity.User;
import com.mrl.pastry.portal.model.params.UserEditParam;
import com.mrl.pastry.portal.model.params.WxAuthParam;
import com.mrl.pastry.portal.model.vo.UserAuthVO;
import com.mrl.pastry.portal.model.vo.UserStatisticVO;
import com.mrl.pastry.portal.service.UserService;
import com.mrl.pastry.portal.utils.RedisUtils;
import com.mrl.pastry.portal.utils.SensitiveUtils;
import com.mrl.pastry.portal.utils.WxAuthUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User service implementation
 *
 * @author MrL
 * @since 2021-03-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final ApplicationEventPublisher eventPublisher;

    private final WxAuthUtils wxAuthUtils;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final RedisUtils redisUtils;

    private final SensitiveUtils sensitiveUtils;

    @Override
    public Optional<User> getOneUserByQueryWrapper(@NonNull Wrapper<User> wrapper) {
        return Optional.ofNullable(getOne(wrapper));
    }

    @Override
    public List<String> getUserRolesById(@NonNull Long userId) {
        return Collections.singletonList(DefaultConstant.USER_ROLE);
    }

    @Override
    public UserAuthVO authenticate(@NonNull WxAuthParam param) {
        Assert.notNull(param, "auth info must not be null");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                throw new BadRequestException("请勿重复认证");
            }
        }
        JSONObject authInfo = wxAuthUtils.getAuthInfo(param.getCode());
        if (!Objects.isNull(authInfo.getObj("errcode"))) {
            throw new BadRequestException("获取用户信息失败");
        }
        // Check if the user exists
        String openId = authInfo.get("openid", String.class);
        Optional<User> optionalUser = getOneUserByQueryWrapper(Wrappers.<User>lambdaQuery().eq(User::getOpenId, openId));
        if (optionalUser.isPresent()) {
            // refresh user's token
            User user = optionalUser.get();
            String token = buildToken(user.getId());
            UserDetailDTO userDetailDTO = BeanUtils.copyProperties(user, UserDetailDTO.class);
            return new UserAuthVO(userDetailDTO, token);
        } else {
            // register a new user
            String sessionKey = authInfo.get("session_key", String.class);
            JSONObject userInfo = wxAuthUtils.getUserInfo(sessionKey, param.getEncryptedData(), param.getIv());

            User user = new User();
            user.setOpenId(openId);
            user.setNickname(userInfo.get("nickName", String.class));
            user.setAvatar(userInfo.get("avatarUrl", String.class));
            user.setGender(userInfo.get("gender", Integer.class) == 1);
            user.setUsername(user.getNickname());
            user.setPassword(passwordEncoder.encode(DefaultConstant.USER_INITIAL_PASSWORD));
            user.setCoinCount(10);
            save(user);
            log.debug("register a new user: [{}]", user);

            // publish register event
            eventPublisher.publishEvent(new UserNewEvent(this, user));

            String token = buildToken(user.getId());
            UserDetailDTO userDetailDTO = BeanUtils.copyProperties(user, UserDetailDTO.class);
            return new UserAuthVO(userDetailDTO, token);
        }
    }

    private String buildToken(Long userId) {
        HashMap<String, Object> claims = MapUtil.newHashMap(1);
        claims.put("role", getUserRolesById(userId));
        return jwtUtils.generateToken(claims, userId.toString());
    }

    @Override
    public UserBaseDTO getUserDto(@NonNull Long userId) {
        return redisUtils.cacheable(RedisConstant.USER_NAMESPACE, userId + "",
                () -> this.baseMapper.getUserBaseDtoById(userId), UserBaseDTO.class);
    }

    @Override
    public UserDetailDTO getUserDetailInfo(@NonNull Long userId) {
        Assert.notNull(userId, "userId must not be null");
        return this.baseMapper.getUserDetailDtoById(userId);
    }

    @Override
    public UserStatisticVO getUserStatistic(@NonNull Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        UserStatisticVO statisticVO = this.baseMapper.getUserStatisticVoById(userId);
        if (!currentUserId.equals(userId)) {
            statisticVO.setCoinCount(0);
        }
        return statisticVO;
    }

    @Override
    public UserDetailDTO editUserInfo(@NonNull UserEditParam param) {
        Long userId = SecurityUtils.getCurrentUserId();
        // delete the cached key anyway
        redisUtils.evict(RedisConstant.USER_NAMESPACE, userId + "");

        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getId, userId)
                .select(User::getAvatar, User::getNickname, User::getCoinCount));
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.<User>lambdaUpdate().eq(User::getId, userId);
        int cost = 0;
        if (!param.getNickname().equals(user.getNickname())) {
            // exclude sensitive words
            String nickname = sensitiveUtils.refine(param.getNickname());
            updateWrapper.set(User::getNickname, nickname);
            cost += 5;
        }
        if (!param.getAvatar().equals(user.getAvatar())) {
            updateWrapper.set(User::getAvatar, param.getAvatar());
            cost += 2;
        }
        if (user.getCoinCount() < cost) {
            throw new BadRequestException("硬币不足");
        } else if (cost != 0) {
            updateWrapper.set(User::getCoinCount, user.getCoinCount() - cost);
        }
        // exclude sensitive words
        String profile = sensitiveUtils.refine(param.getProfile());
        update(updateWrapper.set(User::getProfile, profile)
                .set(User::getGender, param.getGender()));
        log.debug("updated userId:[{}] user:[{}] param:[{}]", userId, user, param);
        return getUserDetailInfo(userId);
    }

    @Override
    public void decreaseCoinCount(@NonNull Integer coin) {
        Long userId = SecurityUtils.getCurrentUserId();
        int result = this.baseMapper.increaseCoinCount(userId, -coin);
        log.debug("decrease user:[{}] coin:[{}], result:[{}]", userId, -coin, result);
        if (result != 1) {
            throw new BadRequestException("硬币数量不足!");
        }
    }
}
