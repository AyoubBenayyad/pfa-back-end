package com.example.demo.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByEmail() {
        User user = new User(Long.parseLong("1"),"IBRAHIM",
                "BENZEKRI",
                "test@gmail.com",
                "password",
                null);
        Optional<User> user2 = Optional.of(user);
        underTest.save(user);

        Optional<User> result = underTest.findByEmail("test@gmail.com");

        assertEquals(result,user2);
    }

    @Test
    void findByEmailNotFound() {
        Optional<User> result = underTest.findByEmail("test@gmail.com");

        assertEquals(result,Optional.empty());
    }

    @Test
    void existsUserByEmailFound() {
        User user = new User(Long.parseLong("1"),"IBRAHIM",
                "BENZEKRI",
                "test@gmail.com",
                "password",
                null);

        underTest.save(user);


       boolean result = underTest.existsUserByEmail("test@gmail.com");

       assertTrue(result);

    }

    @Test
    void existsUserByEmailNotFound() {

       boolean result = underTest.existsUserByEmail("test@gmail.com");

       assertFalse(result);

    }


}