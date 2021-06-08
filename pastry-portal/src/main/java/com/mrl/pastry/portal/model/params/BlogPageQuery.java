package com.mrl.pastry.portal.model.params;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.mrl.pastry.portal.model.entity.Blog;
import com.mrl.pastry.portal.model.page.AbstractScrollPageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Blog page query
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/11
 */
@Deprecated
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogPageQuery extends AbstractScrollPageQuery<Blog> {

    private Long sort;

    private Boolean next;

    @Override
    protected SFunction<Blog, ?> getSortColumnFunc() {
        return Blog::getId;
    }
}
