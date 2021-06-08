package com.mrl.pastry.portal.model.page;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * MybatisPlus page query
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/14
 */
@Deprecated
public interface PageQuery<T> {

    /**
     * Wrap page request entity
     *
     * @return Page
     */
    Page<T> wrapPage();

    /**
     * Customized query wrapper
     *
     * @return lambdaQueryWrapper
     */
    Wrapper<T> customizedQueryWrapper();
}
