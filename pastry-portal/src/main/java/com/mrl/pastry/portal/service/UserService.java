package com.mrl.pastry.portal.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.pastry.portal.model.dto.UserBaseDTO;
import com.mrl.pastry.portal.model.dto.UserDetailDTO;
import com.mrl.pastry.portal.model.entity.User;
import com.mrl.pastry.portal.model.params.UserEditParam;
import com.mrl.pastry.portal.model.params.WxAuthParam;
import com.mrl.pastry.portal.model.vo.UserAuthVO;
import com.mrl.pastry.portal.model.vo.UserStatisticVO;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * User service
 *
 * @author MrL
 * @since 2021-03-07
 */
public interface UserService extends IService<User> {

    /**
     * Gets user by query wrapper
     *
     * @param wrapper lambdaQueryWrapper
     * @return an optional with User
     */
    Optional<User> getOneUserByQueryWrapper(@NonNull Wrapper<User> wrapper);

    /**
     * Get user's roles by id
     *
     * @param userId user id
     * @return list of roles
     */
    List<String> getUserRolesById(@NonNull Long userId);

    /**
     * Authenticate user info
     *
     * @param userInfo wxLogin param
     * @return UserAuthVO
     */
    @Transactional(rollbackFor = Exception.class)
    UserAuthVO authenticate(@NonNull WxAuthParam userInfo);

    /**
     * Get User dto
     *
     * @param userId userId
     * @return BaseUserDTO
     */
    UserBaseDTO getUserDto(@NonNull Long userId);

    /**
     * Gets userDetailDTO
     *
     * @param userId must not be null
     * @return UserDetailDTO
     */
    UserDetailDTO getUserDetailInfo(Long userId);

    /**
     * Get user statistics
     *
     * @param userId must not be null
     * @return UserStatisticVO
     */
    UserStatisticVO getUserStatistic(@NonNull Long userId);

    /**
     * Edit user info
     *
     * @param user must not be null
     * @return UserDetailDTO
     */
    @Transactional(rollbackFor = Exception.class)
    UserDetailDTO editUserInfo(@NonNull UserEditParam user);

    /**
     * Decrease user's coin
     *
     * @param coin must not be null
     */
    void decreaseCoinCount(@NonNull Integer coin);
}
