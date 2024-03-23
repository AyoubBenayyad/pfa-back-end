package com.example.demo.Comments;

import com.example.demo.Rating.RatingRequest;

import java.util.List;

public interface IcommentService {
    public void addComment(CommentRequest request,String email_commentingUser);

    List<CommentResponse> getPostComments(Long postId) throws Exception;
}
