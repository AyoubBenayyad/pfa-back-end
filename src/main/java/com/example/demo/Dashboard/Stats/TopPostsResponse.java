package com.example.demo.Dashboard.Stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class TopPostsResponse {
    private String title;
    private TopUsersResponse user;
    private Long interactions;
    private String type;
}
