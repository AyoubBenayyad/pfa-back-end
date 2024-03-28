package com.example.demo.Dashboard;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdminInfosResponse {
    private String fullName;
    private String email;
    private String imageUrl;
}
