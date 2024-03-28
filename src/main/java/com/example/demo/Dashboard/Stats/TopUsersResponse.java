package com.example.demo.Dashboard.Stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class TopUsersResponse {
    private String fullName;
    private String email;
    private String imageUrl;
    private float rating;
}
