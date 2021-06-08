package com.mrl.pastry.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrl.pastry.portal.model.vo.BlogVO;
import com.mrl.pastry.portal.service.RankService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rank controller
 *
 * @author MrL
 * @version 1.0
 * @date 2021/4/26
 */
@RestController
@RequestMapping("/rank")
public class RankController {

    private final RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping("list")
    @ApiOperation("Gets trending blogs")
    public IPage<BlogVO> getTrend() {
        return rankService.getRankList();
    }
}
