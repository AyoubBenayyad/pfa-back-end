package com.example.demo.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserProfileResponse {
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String adress;

    private String cne;
    private String bio;
    private String niveau;
    private String filiere;
    private String image;
}
