package com.example.demo.Domains;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainRepo extends JpaRepository<Domain,Long> {
Domain findByName(String name);
}
