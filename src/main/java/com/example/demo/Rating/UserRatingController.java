package com.example.demo.Rating;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserRatingController {

    private final RatingServiceImpl ratingService;

    public UserRatingController(RatingServiceImpl ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping(path = "/rate",produces = "application/json")
    public ResponseEntity<?> Rate(@RequestBody RatingRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        ratingService.rateUser(request,username);
        return ResponseEntity.ok("rated successfully");
    }

}
