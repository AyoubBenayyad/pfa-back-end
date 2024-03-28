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

import java.util.Date;
import java.util.List;

public interface AnnonceRepo extends JpaRepository<Annonce,Long> {

    @Query("select off from Offre off where off.mark > 40 order by off.mark desc")
    Page<Offre> topPosts(Pageable pageable);

    @Query("select count (off) from Offre off where off.mark > 40")
    int countPostsMax();


    Annonce findAnnonceById(Long Id);

    @Query("SELECT distinct COUNT(p) FROM Offre p WHERE EXISTS (SELECT 1 FROM p.domains d WHERE d IN :domains)")
    Long countPostsFiliere(@Param("domains") List<Domain> domains);


    @Query("select count(p)  from Offre p where p.publicationDate>= :start and p.publicationDate<= :end")
    Long countPosts(@Param("start") Date start, @Param("end") Date end);

@Query("select count(p)  from Question p where p.publicationDate>= :start and p.publicationDate<= :end")
    Long countQuestions(@Param("start") Date start, @Param("end") Date end);

    @Query("select count (off) from Offre off  ")
    Long countOffre();
    @Query("select count (off) from Question off  ")
    Long countQuestion();

    @Query("select count (off) from Offre off where off.typeAnnonce = :type ")
    Long countOffreType(@Param("type") OffreType type);

@Query("select count (off) from Offre off where off.typeAnnonce = :type and off.city = :city")
    Long countOffreTypeByCity(@Param("type") OffreType type,@Param("city") String city);



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
