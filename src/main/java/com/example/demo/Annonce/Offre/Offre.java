package com.example.demo.Annonce.Offre;

import com.example.demo.Annonce.Annonce;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("offre")
public class Offre extends Annonce {
    private String city;
    private String entreprise;
    private OffreType typeAnnonce;
}
