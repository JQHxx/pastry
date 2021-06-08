package com.mrl.pastry.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.pastry.portal.model.dto.UserBaseDTO;
import com.mrl.pastry.portal.model.dto.UserDetailDTO;
import com.mrl.pastry.portal.model.entity.User;
import com.mrl.pastry.portal.model.vo.UserStatisticVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.NonNull;

/**
 * User blog mapper
 *
 * @author MrL
 * @since 2021-03-07
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * Get base user dto by id
     *
     * @param userId must not be null
     * @return BaseUserDTO
     */
    UserBaseDTO getUserBaseDtoById(@NonNull Long userId);

    /**
     * Get user detail dto by id
     *
     * @param userId must not be null
     * @return UserDetailDTO
     */
    UserDetailDTO getUserDetailDtoById(@NonNull Long userId);

    /**
     * Get user statistic vo by id
     *
     * @param userId must not be null
     * @return UserStatisticVO
     */
    UserStatisticVO getUserStatisticVoById(@NonNull Long userId);

    /**
     * Increase or decrease user's coin count
     *
     * @param userId   must not be null
     * @param increase 1 / -1
     * @return 1:success
     */
    @Update("update user set coin_count = coin_count + #{increase} where id = #{id} and coin_count + #{increase} >= 0")
    int increaseCoinCount(@NonNull @Param("id") Long userId, @NonNull @Param("increase") Integer increase);

    /**
     * Increase or decrease blog count
     *
     * @param userId   must not be null
     * @param increase 1 / -1
     * @return 1:success
     */
    @Update("update user set blog_count = blog_count + #{increase} where id = #{id} and blog_count + #{increase} >= 0")
    int increaseBlogCount(@NonNull @Param("id") Long userId, @NonNull @Param("increase") Integer increase);

    /**
     * Increase or decrease follow count
     *
     * @param userId   must not be null
     * @param increase 1 / -1
     * @return 1:success
     */
    @Update("update user set follow_count = follow_count + #{increase} where id = #{id} and follow_count + #{increase} >= 0")
    int increaseFollowCount(@NonNull @Param("id") Long userId, @NonNull @Param("increase") Integer increase);

    /**
     * Increase or decrease fans count
     *
     * @param userId   must not be null
     * @param increase 1 / -1
     * @return 1:success
     */
    @Update("update user set fans_count = fans_count + #{increase} where id = #{id} and fans_count + #{increase} >= 0")
    int increaseFansCount(@NonNull @Param("id") Long userId, @NonNull @Param("increase") Integer increase);
}
