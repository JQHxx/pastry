package com.mrl.pastry.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrl.pastry.portal.model.dto.BlogBaseDTO;
import com.mrl.pastry.portal.model.entity.Blog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.NonNull;

/**
 * Blog Mapper
 *
 * @author MrL
 * @since 2021-03-07
 */
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * Get blog visit count
     *
     * @param blogId blog Id must not be null
     * @return visit count
     */
    @Select("select visit_count from blog where id = #{blogId}")
    Integer getVisitCount(@NonNull @Param("blogId") Long blogId);

    /**
     * Get blog like count
     *
     * @param blogId blog Id must not be null
     * @return like count
     */
    @Select("select like_count from blog where id = #{blogId}")
    Integer getLikeCount(@NonNull @Param("blogId") Long blogId);

    /**
     * Get blog comment count
     *
     * @param blogId blog Id must not be null
     * @return comment count
     */
    @Select("select comment_count from blog where id = #{blogId}")
    Integer getCommentCount(@NonNull @Param("blogId") Long blogId);

    /**
     * Get blog coin count
     *
     * @param blogId blog Id must not be null
     * @return coin count
     */
    @Select("select coin_count from blog where id = #{blogId}")
    Integer getCoinCount(@NonNull @Param("blogId") Long blogId);

    /**
     * Get blog coin count
     *
     * @param userId must not be null
     * @return blog count
     */
    @Select("select count(1) from blog where author = #{userId} and stat")
    Integer getBlogCount(@NonNull @Param("userId") Long userId);

    /**
     * Get blog base dto
     *
     * @param blogId blog id must not be null
     * @return BlogDTO
     */
    BlogBaseDTO getBlogBaseDTO(@NonNull Long blogId);

    /**
     * Update blog statistics(like,visit)
     *
     * @param blogId must not be null
     * @param like   like_count
     * @param visit  views
     */
    void updateBlogStatistic(@NonNull @Param("blogId") Long blogId, @Param("like") Integer like, @Param("visit") Integer visit);

    /**
     * Increases comment count
     *
     * @param blogId   must not be null
     * @param increase must not be null
     */
    @Update("update blog set comment_count = comment_count + #{increase} where id = #{blogId}")
    void increaseCommentCount(@NonNull @Param("blogId") Long blogId, @NonNull @Param("increase") Integer increase);

    /**
     * Increases coin count
     *
     * @param blogId   must not be null
     * @param increase must not be null
     */
    @Update("update blog set coin_count = coin_count + #{increase} where id = #{blogId}")
    void increaseCoinCount(@NonNull @Param("blogId") Long blogId, @NonNull @Param("increase") Integer increase);
}
