package com.mrl.pastry.portal.model.page;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * Abstract scroll page query
 * 弃用原因：scroll view本质上就一滑动窗口，刷新操作直接加载最新的一页即可（保证窗口上界，只扩展下界），
 * 最初的想法是两端都扩展，实现起来很复杂也没必要
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/27
 */
@Deprecated
public abstract class AbstractScrollPageQuery<T> extends AbstractSimplePageQuery<T> {

    /**
     * Distributed id ensures that all blog ids are incremented over time and not duplicated
     *
     * @return the value of sort column, e.g. sort(id:100)
     */
    protected abstract Object getSort();

    /**
     * Getter function of sort column
     *
     * @return FunctionalInterface, e.g. Entity::getId
     */
    protected abstract SFunction<T, ?> getSortColumnFunc();

    /**
     * the latest page : true
     * the previous page : false
     *
     * @return Boolean
     */
    protected abstract Boolean getNext();

    @Override
    public Wrapper<T> customizedQueryWrapper() {
        LambdaQueryWrapper<T> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if (getSort() == null) {
            // default: the latest data
            lambdaQueryWrapper.orderByDesc(getSortColumnFunc());
        } else {
            if (getNext()) {
                // need to reverse
                lambdaQueryWrapper.gt(getSortColumnFunc(), getSort()).orderByAsc(getSortColumnFunc());
            } else {
                // page +1
                lambdaQueryWrapper.lt(getSortColumnFunc(), getSort()).orderByDesc(getSortColumnFunc());
            }
        }
        return lambdaQueryWrapper;
    }
}
