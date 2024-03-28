package com.example.demo.Dashboard;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UsersTableResponse {
    private Long id;
    private String fullName;
    private String  email;
    private String imageUrl;
    private Boolean status;

}
