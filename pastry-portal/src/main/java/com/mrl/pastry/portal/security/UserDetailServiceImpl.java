package com.mrl.pastry.portal.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mrl.pastry.portal.model.entity.User;
import com.mrl.pastry.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UserDetailService implementation
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/17
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) {
        Optional<User> user = userService.getOneUserByQueryWrapper(Wrappers.<User>lambdaQuery()
                .eq(User::getId, username).select(User::getId, User::getPassword));

        return user.map(u -> new org.springframework.security.core.userdetails.User(username, u.getPassword(),
                userService.getUserRolesById(u.getId()).parallelStream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
        )).orElseThrow(() -> new UsernameNotFoundException("user does not exist"));
    }
}
