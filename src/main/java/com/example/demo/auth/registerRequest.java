package com.example.demo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class registerRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
