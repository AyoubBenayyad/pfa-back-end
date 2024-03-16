package com.example.demo.Annonce;


import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class AnnonceRequest {
    String Title;
    String Description;
    List<String> images;
    String city;
    String entreprise;
    String type;
    List<String> domains;
}
