package com.mrl.pastry.common.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

/**
 * Default UserDetailsService
 *
 * @author MrL
 * @version 1.0
 * @date 2021/6/7
 */
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        // 这里返回一个空权限的user, 考虑将权限信息放入jwt, 在JwtAuthFilter中解析出权限并植入user
        return new User(username, "", Collections.emptyList());
    }
}
