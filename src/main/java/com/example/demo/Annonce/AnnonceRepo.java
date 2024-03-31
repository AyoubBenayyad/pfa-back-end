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


@Query("select off from Annonce off where off.type='offer' and off.Id = :id")
Annonce findOfferById(@Param("id") Long id);

    @Query("select off from Annonce off where off.type = 'offer' and off.mark > 40 order by off.mark desc")
    Page<Annonce> topPosts(Pageable pageable);

    @Query("select count (off) from Annonce off where off.type='offer' and off.mark > 40")
    int countPostsMax();


    Annonce findAnnonceById(Long Id);

    @Query("SELECT distinct COUNT(p) FROM Annonce p WHERE p.type = 'offer' and EXISTS (SELECT 1 FROM p.domains d WHERE d IN :domains)")
    Long countPostsFiliere(@Param("domains") List<Domain> domains);


    @Query("select count(p)  from Annonce p where p.type = 'offer' and p.publicationDate>= :start and p.publicationDate<= :end")
    Long countPosts(@Param("start") Date start, @Param("end") Date end);

@Query("select count(p)  from Annonce p where p.type = 'question' and p.publicationDate>= :start and p.publicationDate<= :end")
    Long countQuestions(@Param("start") Date start, @Param("end") Date end);

    @Query("select count (off) from Annonce off where off.type = 'offer'")
    Long countOffre();
    @Query("select count (off) from Annonce off  where off.type = 'question'")
    Long countQuestion();

    @Query("select count (off) from Annonce off where off.typeAnnonce = :type ")
    Long countOffreType(@Param("type") OffreType type);

@Query("select count (off) from Annonce off where off.typeAnnonce = :type and off.city = :city")
    Long countOffreTypeByCity(@Param("type") OffreType type,@Param("city") String city);


    @Query("SELECT DISTINCT p FROM Annonce p  WHERE " +
            "(:domain IS NULL OR :domain MEMBER OF p.domains)  " +
            "AND (:postType IS NULL OR p.typeAnnonce = :postType) " +
            "AND (:city IS NULL OR p.city = :city)" +
            " AND (p.userPosting.id <> :id)")
    Page<Annonce> filteredPosts(
            @Param("domain") Domain domain,
            @Param("postType") OffreType postType,
            @Param("city") String city,
            @Param("id") Long id,
            Pageable pageable
    );


}
