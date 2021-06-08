package com.mrl.pastry.common.utils;

import com.mrl.pastry.common.exception.UnAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Security utilities
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/28
 */
public class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get current username(id) from SecurityContextHolder
     *
     * @return userId
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (null != authentication && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return Optional.ofNullable(username).map(Long::parseLong)
                .orElseThrow(() -> new UnAuthorizedException("未授权访问"));
    }
}
