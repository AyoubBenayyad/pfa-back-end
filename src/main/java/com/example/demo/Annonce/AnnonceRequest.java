package com.example.demo.Annonce;

import com.example.demo.Annonce.Offre.OffreType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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
