package com.example.demo.Rating;

public interface IRatingService {

    public void rateUser(RatingRequest request, Long Id_ratingUser) throws Exception;

    RatingResponse getUserRating(Long userId);

    int getRatingByCurrentUser(Long OnlineUserid, Long userId);
}
