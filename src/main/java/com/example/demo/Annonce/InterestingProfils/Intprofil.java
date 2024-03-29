package com.example.demo.Annonce.InterestingProfils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Intprofil {
    Long id;
    String imageUrl;
    String fullName;
    String email;
    float rating;
}
