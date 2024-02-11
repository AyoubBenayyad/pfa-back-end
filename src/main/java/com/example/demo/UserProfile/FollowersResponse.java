package com.example.demo.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FollowersResponse {
    private Long id;
    private String name;
    private String image;
}
