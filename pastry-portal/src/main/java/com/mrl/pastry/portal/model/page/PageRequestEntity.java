package com.mrl.pastry.portal.model.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Simple Page query
 * 弃用原因：直接使用Jpa#Pageable即可；
 * 否则还得实现一个分页注解，一个切方法参数的切面
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/27
 */
@Deprecated
@Data
public class PageRequestEntity implements Serializable {

    private int page;

    private int size;

    private List<OrderItem> sorts;
}
