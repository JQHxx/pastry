package com.mrl.pastry.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrl.pastry.portal.model.params.BlogPostParam;
import com.mrl.pastry.portal.model.vo.BlogVO;
import com.mrl.pastry.portal.service.BlogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Blog controller
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/10
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("page")
    @ApiOperation("Gets the latest or previous page of blogs")
    public IPage<BlogVO> getPage(@PageableDefault(sort = "id", direction = DESC) Pageable pageable,
                                 @RequestParam(value = "limit", required = false) Long limit,
                                 @RequestParam(value = "userId", required = false) Long userId) {
        // TODO: 分组
        return blogService.getPage(pageable, limit, userId);
    }

    @PostMapping("like/{blogId:\\d+}")
    @ApiOperation("Give a like or cancel the like")
    public Boolean like(@PathVariable("blogId") Long blogId) {
        return blogService.doLikeBlog(blogId);
    }

    @PostMapping("coin/{blogId:\\d+}")
    @ApiOperation("Gives a coin")
    public String coin(@PathVariable("blogId") Long blogId) {
        blogService.doGiveCoin(blogId);
        return "ok";
    }

    @PostMapping("post")
    @ApiOperation("Posts a blog")
    public String post(@Valid @RequestBody BlogPostParam blogParam) {
        blogService.post(blogParam);
        return "ok";
    }

    @DeleteMapping("delete/{blogId:\\d+}")
    @ApiOperation("Deletes a blog")
    public String delete(@PathVariable("blogId") Long id) {
        blogService.deleteBlog(id);
        return "ok";
    }
}
