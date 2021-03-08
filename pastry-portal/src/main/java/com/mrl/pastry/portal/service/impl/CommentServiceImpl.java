package com.mrl.pastry.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.portal.mapper.CommentMapper;
import com.mrl.pastry.portal.model.entity.Comment;
import com.mrl.pastry.portal.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
