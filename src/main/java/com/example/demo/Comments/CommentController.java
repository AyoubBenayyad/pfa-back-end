package com.example.demo.Comments;


import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    CommentServiceImpl commentService;

    @PostMapping(path = "/addComment")
    public ResponseEntity<?> commenting(@RequestBody CommentRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        commentService.addComment(request, username);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Comment saved successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/getPostComments/{PostId}")
    public ResponseEntity<?> getPostComments(@PathVariable Long PostId) throws Exception {
        List<CommentResponse> postComments = commentService.getPostComments(PostId);

        return ResponseEntity.ok(postComments);
    }

    @DeleteMapping(path = "/deleteComment/{CommentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long CommentId, @AuthenticationPrincipal User user) throws Exception {

        try {
            commentService.deleteComment(CommentId, user.getId());
            HashMap<String, Object> response = new HashMap<>();
            response.put("result", "comment with id " + CommentId + " deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you dont have the right to delete this comment");
        }

    }


}
