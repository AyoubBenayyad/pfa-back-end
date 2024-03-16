package com.example.demo.user;

import com.example.demo.Annonce.Annonce;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    boolean existsUserByEmail(String email);
    boolean existsUserByCne(CNE cne);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableAppUser(String email);


    @Query("SELECT a FROM Annonce a WHERE a.userPosting.id = :userId ORDER BY a.publicationDate DESC ")
    List<Annonce> findAllAnnoncesByUserPostingOrderByPublicationDate(@Param("userId") Long userId);
}
