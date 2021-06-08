package com.mrl.pastry.portal.model.params;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.mrl.pastry.portal.model.entity.Comment;
import com.mrl.pastry.portal.model.page.AbstractScrollPageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * Comment page query
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/27
 */
@Deprecated
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentPageQuery extends AbstractScrollPageQuery<Comment> {

    private Long sort;

    private Boolean next;

    @NotNull(message = "parentId must not be null")
    private Long parentId;

    @Override
    protected SFunction<Comment, ?> getSortColumnFunc() {
        return Comment::getId;
    }

    @Override
    public Wrapper<Comment> customizedQueryWrapper() {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = (LambdaQueryWrapper<Comment>) super.customizedQueryWrapper();
        return lambdaQueryWrapper.eq(Comment::getParentId, parentId);
    }
}
