package com.mrl.pastry.portal.model.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * Abstract page query
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/27
 */
@Deprecated
public abstract class AbstractSimplePageQuery<T> extends PageRequestEntity implements PageQuery<T> {

    @Override
    public Page<T> wrapPage() {
        Page<T> page = new Page<>(getPage(), getSize());
        // it's ok to set null
        page.setOrders(getSorts());
        return page;
    }
}
