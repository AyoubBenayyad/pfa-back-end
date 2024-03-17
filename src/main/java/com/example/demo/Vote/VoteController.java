package com.example.demo.Vote;

import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/Vote")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/{postId}/UpVote")
    public ResponseEntity<?> UpVotePost(@PathVariable Long postId,@AuthenticationPrincipal User user) throws Exception {

        try{
            voteService.UpVote(postId,user.getId());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Post UpVoted");
            return ResponseEntity.ok(response);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());}

        }



    @PostMapping("/{postId}/DownVote")
    public ResponseEntity<?> DownVotePost(@PathVariable Long postId,@AuthenticationPrincipal User user) throws Exception {
        try{
            voteService.DownVote(postId, user.getId());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Post Downvoted");
            return ResponseEntity.ok(response);


        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());        }
    }

    @PostMapping("/{postId}/RemoveUpVote")
    public ResponseEntity<?> RemoveUpVote(@PathVariable Long postId,@AuthenticationPrincipal User user) throws Exception {

        try{
            voteService.RemoveUpVote(postId, user.getId());
            Map<String, String> response = new HashMap<>();
            response.put("message", "UpVote Removed");
            return ResponseEntity.ok(response);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());}

    }

    @PostMapping("/{postId}/RemoveDownVote")
    public ResponseEntity<?> RemoveDownVote(@PathVariable Long postId,@AuthenticationPrincipal User user) throws Exception {

        try{
            voteService.RemoveDownVote(postId, user.getId());
            Map<String, String> response = new HashMap<>();
            response.put("message", "DownVoteRemoved");
            return ResponseEntity.ok(response);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());}
    }

    @GetMapping("/{postId}/isVoted")
    public ResponseEntity<?> isVoted(@PathVariable Long postId,@AuthenticationPrincipal User user) throws Exception {

        try{
            VoteResponseDto voted = voteService.isVoted(postId, user.getId());



            return ResponseEntity.ok(voted);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());}
    }
}
