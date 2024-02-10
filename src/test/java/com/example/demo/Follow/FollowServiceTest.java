package com.example.demo.Follow;

import com.example.demo.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock private UserRepository userRepository;
    private FollowService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FollowService(userRepository);
    }


    @Test
    void followUser() {
    }

    @Test
    void unfollowUser() {
    }
}