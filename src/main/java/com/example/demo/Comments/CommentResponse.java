package com.example.demo.Comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class CommentResponse {
    String commentImg;
    String commentUsername;
    String commentDate;

    String commentText;
    Long commentId;
    Long userId;
}
