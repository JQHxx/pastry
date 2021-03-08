package com.mrl.pastry.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.portal.mapper.TagMapper;
import com.mrl.pastry.portal.model.entity.Tag;
import com.mrl.pastry.portal.service.TagService;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
