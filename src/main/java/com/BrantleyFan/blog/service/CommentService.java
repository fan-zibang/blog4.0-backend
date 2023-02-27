package com.BrantleyFan.blog.service;

import com.BrantleyFan.blog.vo.Result;
import com.BrantleyFan.blog.vo.params.CommentParams;
import com.BrantleyFan.blog.vo.params.PageParams;

public interface CommentService {
    Result getCommentList(PageParams pageParams);

    Result publishComment(CommentParams commentParams);

    Result getCommentCount();

    Result getCommentCountByLevelOne();

    Result deleteComment(int id);
}
