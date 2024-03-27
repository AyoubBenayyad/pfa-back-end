package com.example.demo.Rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RatingResponse {
    float rating;
    int numberOfVotes;
}
