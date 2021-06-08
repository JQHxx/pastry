package com.mrl.pastry.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.portal.mapper.UserBlogMapper;
import com.mrl.pastry.portal.model.entity.UserBlog;
import com.mrl.pastry.portal.service.UserBlogService;
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
public class UserBlogServiceImpl extends ServiceImpl<UserBlogMapper, UserBlog> implements UserBlogService {

}
