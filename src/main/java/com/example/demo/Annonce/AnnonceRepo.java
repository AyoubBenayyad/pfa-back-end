package com.example.demo.Annonce;

import com.example.demo.Annonce.Offre.Offre;
import com.example.demo.Annonce.Offre.OffreType;
import com.example.demo.Domains.Domain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnonceRepo extends JpaRepository<Annonce,Long> {

    Annonce findAnnonceById(Long Id);

    @Query("SELECT DISTINCT p FROM Offre p  WHERE " +
            "(:domain IS NULL OR :domain MEMBER OF p.domains)  " +
            "AND (:postType IS NULL OR p.typeAnnonce = :postType) " +
            "AND (:city IS NULL OR p.city = :city)" +
            " AND (p.userPosting.id <> :id)")
    Page<Offre> filteredPosts(
            @Param("domain") Domain domain,
            @Param("postType") OffreType postType,
            @Param("city") String city,
            @Param("id") Long id,
            Pageable pageable
    );


}
