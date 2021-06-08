package com.mrl.pastry.common.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Iterator;

/**
 * Page utilities
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/17
 */
public class PageUtils {

    /**
     * Convert jpa#Pageable to mybatis-plus#Page
     *
     * @param pageable must not be null
     * @param <T>      entity type
     * @return Page
     */
    public static <T> Page<T> convertToPage(@NonNull Pageable pageable) {
        Assert.notNull(pageable, "pagination information must not be null");
        Page<T> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        Iterator<Sort.Order> iterator = pageable.getSort().stream().iterator();
        while (iterator.hasNext()) {
            Sort.Order order = iterator.next();
            page.addOrder(new OrderItem(order.getProperty(), order.getDirection().isAscending()));
        }
        // 禁止使用count(*)
        page.setSearchCount(false);
        return page;
    }
}
