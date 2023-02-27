package com.BrantleyFan.blog.controller;

import com.BrantleyFan.blog.service.CommentService;
import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.params.CommentParams;
import com.BrantleyFan.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentCount")
    public Result getCommentCount(){
        return commentService.getCommentCount();
    }

    @GetMapping("/commentCountLevelOne")
    public Result getCommentCountByLevelOne(){
        return commentService.getCommentCountByLevelOne();
    }

    @PostMapping("/listComment")
    public Result getCommentList(@RequestBody PageParams pageParams){
        return commentService.getCommentList(pageParams);
    }

    @PostMapping("publishComment")
    public Result publishComment(@RequestBody CommentParams commentParams){
        return commentService.publishComment(commentParams);
    }

    @PostMapping("/delete/{id}")
    public Result deleteComment(@PathVariable("id") int id){
        return commentService.deleteComment(id);
    }
}
