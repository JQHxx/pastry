package com.mrl.pastry.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrl.pastry.common.utils.SecurityUtils;
import com.mrl.pastry.portal.model.vo.FollowVO;
import com.mrl.pastry.portal.service.FollowService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Follow controller
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/26
 */
@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping
    @ApiOperation("Checks whether the user follows the blogger")
    public Boolean isFollow(@RequestParam("bloggerId") Long bloggerId) {
        Long userId = SecurityUtils.getCurrentUserId();
        return followService.isFollow(userId, bloggerId);
    }

    @GetMapping("/{bloggerId:\\d+}")
    @ApiOperation("Gets a page of fans or followers")
    public IPage<FollowVO> getPage(@PageableDefault(sort = "id", direction = DESC) Pageable pageable,
                                   @PathVariable("bloggerId") Long bloggerId,
                                   @RequestParam(value = "fans", required = false, defaultValue = "true") Boolean fans) {
        return followService.getPage(pageable, bloggerId, fans);
    }

    @PostMapping("/{bloggerId:\\d+}")
    @ApiOperation("Follows or unfollows the blogger")
    public Boolean follow(@PathVariable("bloggerId") Long bloggerId) {
        return followService.follow(bloggerId);
    }


    // TODO: 共同关注、可能认识的人
}
