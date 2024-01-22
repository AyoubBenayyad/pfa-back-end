package com.example.demo.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class appRoleRepoTest {

    @Autowired
    private appRoleRepo underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    /*@Test
    void findByRoleNameSucced() {
        appRole role = new appRole(
            "testRole"
        );
        underTest.save(role);

        appRole result = underTest.findByRoleName("testRole");

        assertEquals(result,role);
    }
    @Test
    void findByRoleNameNotSucced() {

        appRole result = underTest.findByRoleName("testRole");

        assertEquals(result,null);
    }*/
}