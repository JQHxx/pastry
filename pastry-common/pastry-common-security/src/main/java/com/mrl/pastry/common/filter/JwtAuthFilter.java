package com.mrl.pastry.common.filter;

import com.mrl.pastry.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt auth filter
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/17
 */
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws IOException, ServletException {
        String tokenPrefix = "Bearer ";
        String username = null, jwtPayload = null;
        String jwtHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtHeader != null && jwtHeader.startsWith(tokenPrefix)) {
            jwtPayload = jwtHeader.substring(tokenPrefix.length()).trim();
            if (!StringUtils.isEmpty(jwtPayload)) {
                username = jwtUtils.extraUserName(jwtPayload);
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtils.isTokenValid(jwtPayload, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                log.debug("SecurityContextHolder set authentication token: [{}]", usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
