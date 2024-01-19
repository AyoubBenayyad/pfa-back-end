package com.example.demo.Config;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigTest {

    @Mock private UserRepository userRepository;
    @Mock private AuthenticationConfiguration authenticationConfiguration;

    private ApplicationConfig underTest;

    @BeforeEach
    void setUp() {
        underTest = new ApplicationConfig(userRepository);
    }

    @Test
    void userDetailsService() {
        String username = "test@gmail.com";
        User user = new User(
                Long.parseLong("1"),
                "IBRAHIM",
                "BENZEKRI",
                "test@gmail.com",
                "password",
                null);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

        // Act
        UserDetailsService userDetailsService = underTest.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        assertTrue(userDetails instanceof User);
        verify(userRepository, times(1)).findByEmail(username);
    }

    @Test
    void userDetailsServiceException() {

        String username = "nonexistent@gmail.com";
        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            UserDetailsService userDetailsService = underTest.userDetailsService();
            userDetailsService.loadUserByUsername(username);
        });

        verify(userRepository, times(1)).findByEmail(username);
    }


    @Test
    @Disabled
    void authenticationProvider() {
        {
        }
    }

    @Test
    void authenticationManager() throws Exception {
        // Arrange
        AuthenticationManager mockAuthenticationManager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mockAuthenticationManager);

        // Act
        AuthenticationManager resultAuthenticationManager = underTest.authenticationManager(authenticationConfiguration);

        // Assert
        assertEquals(mockAuthenticationManager, resultAuthenticationManager);

    }

    @Test
    void passwordEncoder() {
        PasswordEncoder passwordEncoder = underTest.passwordEncoder();

        // Assert
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }
}