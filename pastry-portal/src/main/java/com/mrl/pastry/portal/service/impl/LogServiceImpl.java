package com.mrl.pastry.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.portal.mapper.LogMapper;
import com.mrl.pastry.portal.model.entity.Log;
import com.mrl.pastry.portal.service.LogService;
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
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
