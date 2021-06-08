package com.mrl.pastry.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrl.pastry.portal.mapper.UserLogMapper;
import com.mrl.pastry.portal.model.entity.UserLog;
import com.mrl.pastry.portal.service.LogService;
import org.springframework.stereotype.Service;

/**
 * User log service implementation
 *
 * @author MrL
 * @since 2021-03-07
 */
@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog> implements LogService {

}
