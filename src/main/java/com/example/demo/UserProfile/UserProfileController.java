package com.example.demo.UserProfile;

import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/profile")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping()
    public ResponseEntity<?> getUserById( @AuthenticationPrincipal User user) throws Exception {
        try{

            UserProfileResponse response = userProfileService.getUser(user.getId());
            return ResponseEntity.ok(response);
        }catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/followers")
    public ResponseEntity<?> getFollowers ( @AuthenticationPrincipal User user) throws Exception {
        try{
            List<FollowersResponse> followers = userProfileService.getFollowers(user.getId());
            return  ResponseEntity.ok(followers);
        }catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @GetMapping("/following")
    public ResponseEntity<?> getFollowing ( @AuthenticationPrincipal User user) throws Exception {
        try{
            List<FollowersResponse> followers = userProfileService.getFollowing(user.getId());
            return  ResponseEntity.ok(followers);
        }catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/ProfilePosts")
    public ResponseEntity<?> getProfilePosts(@AuthenticationPrincipal User user){

        List<ProfilePostReponse> profilePosts = userProfileService.getProfilePosts(user);
        return ResponseEntity.ok(profilePosts);
    }

    @GetMapping("/contextUser")
    public ResponseEntity<?> getContextUser(@AuthenticationPrincipal User user) throws Exception {

        try{
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("username", user.getFirstname()+" "+user.getLastname());
            response.put("email", user.getEmail());
            response.put("ImageURL", user.getImageUrl());
            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @PostMapping("/editProfile")
    public ResponseEntity<?> editProfile(@AuthenticationPrincipal User user,@RequestBody EditRequest editRequest){
      try {
          userProfileService.editUser(user,editRequest);
      }catch (Exception e){
          return ResponseEntity.ok(e.getMessage());
      }
        return ResponseEntity.ok("Updated successfully");
    }
}
