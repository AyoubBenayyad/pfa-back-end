package com.example.demo.user;

import com.example.demo.Domains.Domain;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    boolean existsUserByEmail(String email);
    boolean existsUserByCne(CNE cne);
    @Query("SELECT d FROM User u JOIN u.domains d WHERE u.id = :userId")
    List<Domain> findDomainsByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT u FROM User u JOIN u.domains d WHERE" +
            " d IN :domains AND u.id <> :currentUserId " +
            "AND u NOT IN :excludedUsers" +
            " ORDER BY u.rate DESC")
    Page<User> findInterestingUsersExcludingFollowing(
            @Param("domains") List<Domain> domains,
            @Param("currentUserId") Long currentUserId,
            @Param("excludedUsers") Set<User> excludedUsers,
            Pageable pageable
    );

    @Query("SELECT DISTINCT u FROM User u JOIN u.domains d WHERE" +
            " d IN :domains AND u.id <> :currentUserId " +
            " ORDER BY u.rate DESC")
    Page<User> findInterestingUsers(
            @Param("domains") List<Domain> domains,
            @Param("currentUserId") Long currentUserId,
            Pageable pageable
    );

    @Query("select distinct u from User u " +
            "where u.Lastname like %:lname% or u.firstname like %:fname% " +
            "OR u.firstname like %:lname% or u.Lastname like %:fname%")
    List<User> getSearchedUsers(@Param("fname") String fname,
                                              @Param("lname") String lname);


    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableAppUser(String email);
}
