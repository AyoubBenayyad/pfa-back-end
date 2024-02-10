package com.example.demo.Rating;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class RatingRequest {

    Long id_userRated;
    StarRating rate;
}

