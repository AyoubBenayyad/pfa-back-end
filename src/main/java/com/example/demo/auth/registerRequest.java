package com.example.demo.auth;

import com.example.demo.user.CNE;
import jakarta.persistence.Lob;
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
    private String telephone;
    private String adresse;
    private String password;
    private String cne;
    private String biographie;
    private String niveau;
    private String filiere;
    private String image;
}
