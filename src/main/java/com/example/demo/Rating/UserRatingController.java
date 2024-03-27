package com.example.demo.Rating;

import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class UserRatingController {

    private final RatingServiceImpl ratingService;

    public UserRatingController(RatingServiceImpl ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping(path = "/rate",produces = "application/json")
    public ResponseEntity<?> Rate(@RequestBody RatingRequest request,@AuthenticationPrincipal User user) throws Exception {

        try{


            ratingService.rateUser(request, user.getId());
            Map<String, String> response = new HashMap<>();
            response.put("message", "user RatedSuccessfully");
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @GetMapping("/getUserRate/{userId}")
    public ResponseEntity<?> getUserRating(@PathVariable Long userId) throws Exception {



        RatingResponse userRating = ratingService.getUserRating(userId);
        return ResponseEntity.ok(userRating);
    }

    @GetMapping("/ratingByCurrentUser/{userId}")
    public ResponseEntity<?> getRatingByCurrentUser(@PathVariable Long userId,@AuthenticationPrincipal User user) throws Exception {


        int ratingByCurrentUser = ratingService.getRatingByCurrentUser(user.getId(), userId);
        Map<String, Integer> response = new HashMap<>();
        response.put("ratingByCurrentUser", ratingByCurrentUser);
        return ResponseEntity.ok(response);

    }

    }
