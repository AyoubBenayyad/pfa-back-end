package com.example.demo.user;

import com.example.demo.user.appRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface appRoleRepo extends JpaRepository<appRole,Long> {

    appRole findByRoleName(String roleName);
}
