package com.example.demo.Comments;

import com.example.demo.Rating.RatingRequest;

public interface IcommentService {
    public void addComment(CommentRequest request,String email_commentingUser);
}
