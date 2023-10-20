package com.api.Users;

import com.api.Users.entity.UserEntity;
import com.api.Users.repository.UserRepository;
import com.api.Users.security.JwtUtil;
import com.api.Users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignup() {
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setPassword("testPass");

        when(userRepository.findByUsername(eq("testUser"))).thenReturn(Optional.empty());
        when(passwordEncoder.encode(eq("testPass"))).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(jwtUtil.generateToken(eq("testUser"))).thenReturn("testToken");

        String result = userService.signup(user);

        assertEquals("testToken", result);
    }

    @Test
    public void testFindByUsername() {
        UserEntity user = new UserEntity();
        user.setUsername("testUser");

        when(userRepository.findByUsername(eq("testUser"))).thenReturn(Optional.of(user));

        Optional<UserEntity> result = userService.findByUsername("testUser");

        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
    }

    @Test
    public void testLogin() {
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(eq("testUser"))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq("testPass"), eq("encodedPassword"))).thenReturn(true);
        when(jwtUtil.generateToken(eq("testUser"))).thenReturn("testToken");

        String result = userService.login("testUser", "testPass");

        assertEquals("testToken", result);
    }
}
