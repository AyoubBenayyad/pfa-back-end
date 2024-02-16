package com.example.demo.Annonce;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Photos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String image;
    @ManyToOne
    @JoinColumn(name = "annonce_Id")
    Annonce annonce;

    public Photos(String image,Annonce annonce){
        this.image = image;
        this.annonce = annonce;
    }

}
