package com.example.demo.Follow;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/follow/{userId}")
    public ResponseEntity<?> followUser(@PathVariable Long userId) throws Exception {

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            followService.followUser(userId,username);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User followed successfully");
            return ResponseEntity.ok(response);
        }catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/unfollow/{userId}")
    public ResponseEntity<?> unfollowUser(@PathVariable Long userId) throws Exception {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            followService.unfollowUser(userId, username);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User unfollowed successfully");
            return ResponseEntity.ok(response);
        }catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @PostMapping("/removeFollower/{userId}")
    public ResponseEntity<?> removeFollower(@PathVariable Long userId) throws Exception {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            followService.removeFollower(userId, username);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User unfollowed successfully");
            return ResponseEntity.ok(response);
        }catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }
}
