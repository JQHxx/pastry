package com.mrl.pastry.portal.api;

import com.mrl.pastry.common.api.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User feign client service
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/25
 */
public interface UserService {

    /**
     * Get user data
     *
     * @param userId user Id
     * @param detail true: query user with detail info
     * @return user entity
     */
    @GetMapping("/user/userInfo")
    ResponseEntity<?> getUserData(@RequestParam("userId") Long userId,
                                  @RequestParam(value = "detail", required = false, defaultValue = "false") Boolean detail);
}
