package com.mrl.pastry.common;

import com.mrl.pastry.common.config.DefaultUserDetailsServiceImpl;
import com.mrl.pastry.common.config.SecurityConfiguration;
import com.mrl.pastry.common.filter.JwtAuthFilter;
import com.mrl.pastry.common.property.IgnoreProperties;
import com.mrl.pastry.common.property.JwtProperties;
import com.mrl.pastry.common.utils.JwtUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security AutoConfigure
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/19
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(value = {JwtProperties.class, IgnoreProperties.class})
public class SecurityAutoConfigure {

    @Bean
    public SecurityConfiguration securityConfiguration() {
        return new SecurityConfiguration();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public DefaultUserDetailsServiceImpl defaultUserDetailService() {
        return new DefaultUserDetailsServiceImpl();
    }
}
