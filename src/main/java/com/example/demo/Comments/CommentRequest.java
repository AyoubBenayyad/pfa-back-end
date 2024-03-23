package com.example.demo.Comments;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class CommentRequest {
    private Long IdCommentedPost;
    private String comment;

}
