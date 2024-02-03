package com.example.demo.user;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CneRepo extends JpaRepository<CNE,String> {
    boolean existsByCne(String cne);
    CNE findByCne(String cne);
}
