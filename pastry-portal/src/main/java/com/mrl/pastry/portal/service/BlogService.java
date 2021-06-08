package com.mrl.pastry.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrl.pastry.portal.model.dto.BlogBaseDTO;
import com.mrl.pastry.portal.model.entity.Blog;
import com.mrl.pastry.portal.model.params.BlogPostParam;
import com.mrl.pastry.portal.model.vo.BlogStatisticVO;
import com.mrl.pastry.portal.model.vo.BlogVO;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

/**
 * Blog service interface
 *
 * @author MrL
 * @since 2021-03-07
 */
public interface BlogService extends IService<Blog> {

    int MAX_ATTACHMENTS = 9;

    /**
     * Gets the latest or previous page of blogs
     *
     * @param limit    the minimum blogId of previous page
     * @param pageable pagination information
     * @param userId   the bloggerId
     * @return a page of blogVOs
     */
    IPage<BlogVO> getPage(Pageable pageable, Long limit, Long userId);

    /**
     * Creates blog with attachments
     *
     * @param blogParam BlogParam
     */
    @Transactional(rollbackFor = Exception.class)
    void post(@NonNull BlogPostParam blogParam);

    /**
     * Convert to BlogVO
     *
     * @param blog          must not be null
     * @param currentUserId must not be null
     * @return BlogVO
     */
    BlogVO convertToBlogVO(@NonNull Blog blog, @NonNull Long currentUserId);

    /**
     * Gets blog base DTO
     *
     * @param blogId must not be null
     * @return BlogBaseDTO
     */
    BlogBaseDTO getBlogBaseDTO(@NonNull Long blogId);

    /**
     * Get Blog statistics from redis
     *
     * @param blogId blog id must not be null
     * @return BlogStatisticDTO
     */
    BlogStatisticVO getBlogStatistic(@NonNull Long blogId);

    /**
     * delete blog by id
     *
     * @param blogId blogId
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteBlog(@NonNull Long blogId);

    /**
     * Gives a like
     *
     * @param blogId must not be null
     * @return true:like
     */
    Boolean doLikeBlog(@NonNull Long blogId);

    /**
     * Gives a coin
     *
     * @param blogId must not be null
     */
    @Transactional(rollbackFor = Exception.class)
    void doGiveCoin(@NonNull Long blogId);

    /**
     * Checks if user gave a like
     *
     * @param blogId must not be null
     * @param userId must not be null
     * @return true:like
     */
    Boolean checkLike(@NonNull Long blogId, @NonNull Long userId);
}
