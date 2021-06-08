package com.mrl.pastry.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mrl.pastry.portal.model.dto.CommentDTO;
import com.mrl.pastry.portal.model.params.CommentParam;
import com.mrl.pastry.portal.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Comment controller
 *
 * @author MrL
 * @version 1.0
 * @date 2021/3/11
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("post")
    @ApiOperation("Posts a comment")
    public CommentDTO comment(@Valid @RequestBody CommentParam param) {
        return commentService.comment(param);
    }

    @GetMapping("page")
    @ApiOperation("Gets a page of comments")
    public IPage<CommentDTO> getCommentPage(@PageableDefault(sort = {"id"}, direction = DESC) Pageable pageable,
                                            @RequestParam(value = "limit", required = false, defaultValue = "0") Long limit,
                                            @RequestParam(value = "parentId") Long parentId,
                                            @RequestParam(value = "queryChild", required = false, defaultValue = "false") Boolean queryChild) {
        // TODO: 按热度排序
        return commentService.getCommentPage(pageable, limit, parentId, queryChild);
    }

    @DeleteMapping("delete/{commentId:\\d+}")
    @ApiOperation("Deletes a comment")
    public String deleteComment(@PathVariable("commentId") Long id) {
        commentService.deleteComment(id);
        return "ok";
    }

    /*@PostMapping("like/{commentId:\\d+}")
    @ApiOperation("Gives a like or cancel the like")
    public Boolean like(@PathVariable("commentId") Long commentId) {
        //TODO: 待设计
        return false;
    }*/
}
