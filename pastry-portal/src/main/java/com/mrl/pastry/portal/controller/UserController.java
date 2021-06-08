package com.mrl.pastry.portal.controller;

import com.mrl.pastry.portal.model.dto.UserBaseDTO;
import com.mrl.pastry.portal.model.dto.UserDetailDTO;
import com.mrl.pastry.portal.model.params.UserEditParam;
import com.mrl.pastry.portal.model.params.WxAuthParam;
import com.mrl.pastry.portal.model.vo.UserAuthVO;
import com.mrl.pastry.portal.model.vo.UserStatisticVO;
import com.mrl.pastry.portal.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User controller
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("authenticate")
    @ApiOperation("Authenticates and gets jwt token")
    public UserAuthVO wxAuth(@Valid @RequestBody WxAuthParam userInfo) {
        return userService.authenticate(userInfo);
    }

    @GetMapping("userInfo")
    @ApiOperation("Gets user info")
    public UserBaseDTO getUserDetails(@RequestParam("userId") Long userId,
                                      @RequestParam(value = "detail", required = false, defaultValue = "true") Boolean detail) {
        return detail ? userService.getUserDetailInfo(userId) : userService.getUserDto(userId);
    }

    @GetMapping("statistics")
    @ApiOperation("Gets user statistics")
    public UserStatisticVO getUserStatistic(@RequestParam("userId") Long userId) {
        return userService.getUserStatistic(userId);
    }

    @PutMapping("edit")
    @ApiOperation("Edits user info")
    public UserDetailDTO edit(@Valid @RequestBody UserEditParam param) {
        return userService.editUserInfo(param);
    }
}
