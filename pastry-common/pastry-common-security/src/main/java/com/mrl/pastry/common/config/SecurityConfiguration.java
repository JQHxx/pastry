package com.mrl.pastry.common.config;

import cn.hutool.json.JSONUtil;
import com.mrl.pastry.common.api.PastryStatus;
import com.mrl.pastry.common.api.ResponseEntity;
import com.mrl.pastry.common.filter.JwtAuthFilter;
import com.mrl.pastry.common.property.IgnoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security Config
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/17
 */
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IgnoreProperties ignoreProperties;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.GET,
                "/swagger-ui.html",
                "/doc.html",
                "/swagger-resources/**",
                "/swagger/**",
                "/**/*.js",
                "/**/*.css",
                "/**/*.png",
                "/**/*.ico",
                "/v2/api-docs/**",
                "/webjars/springfox-swagger-ui/**",
                "/druid/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, ignoreProperties.getGetUrls())
                .permitAll()
                .antMatchers(HttpMethod.POST, ignoreProperties.getPostUrls())
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest()
                .authenticated();
        http.headers().cacheControl();
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            log.error("caught accessDeniedException: [{}]", accessDeniedException.getMessage());
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().println(JSONUtil.parse(ResponseEntity.set(PastryStatus.FORBIDDEN)));
            response.getWriter().flush();
        };
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            log.error("caught AuthenticationException: [{}]", authException.getMessage());
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().println(JSONUtil.parse(ResponseEntity.set(PastryStatus.UNAUTHORIZED)));
            response.getWriter().flush();
        };
    }
}
