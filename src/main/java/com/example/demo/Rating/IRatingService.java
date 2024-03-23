package com.example.demo.Rating;

public interface IRatingService {

    public void rateUser(RatingRequest request, Long Id_ratingUser) throws Exception;

    int getUserRating(Long userId);
}
