package com.mrl.pastry.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.portal.mapper.CategoryMapper;
import com.mrl.pastry.portal.model.entity.Category;
import com.mrl.pastry.portal.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author MrL
 * @since 2021-03-07
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
