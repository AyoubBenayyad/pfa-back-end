package com.example.demo.Domains;


import com.example.demo.Annonce.Annonce;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    @JsonBackReference
    @ManyToMany(mappedBy = "domains",fetch = FetchType.LAZY)
    private Set<User> DomainUsers = new HashSet<>();
    @JsonBackReference
    @ManyToMany(mappedBy = "domains",fetch = FetchType.LAZY)
    private  Set<Annonce> annonces = new HashSet<>();

    public Domain( String name) {
        this.name = name;
    }
}
