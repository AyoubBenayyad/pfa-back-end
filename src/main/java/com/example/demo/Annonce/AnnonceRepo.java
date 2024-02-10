package com.example.demo.Annonce;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnonceRepo extends JpaRepository<Annonce,Long> {

    Annonce findAnnonceById(Long Id);

}
