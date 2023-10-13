package com.api.Users.service;

import com.api.Users.entity.UserEntity;
import com.api.Users.repository.UserRepository;
import com.api.Users.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling user-related operations.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Registers a new user.
     *
     * @param user The user to register.
     * @return The registered user.
     */
    public String signup(UserEntity user) {
        logger.info("Registering user with username: {}", user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity registeredUser = userRepository.save(user);
        logger.info("User registered with ID: {}", registeredUser.getId());
        String token = jwtUtil.generateToken(registeredUser.getUsername());
        System.out.println("token = " + token);
        return jwtUtil.generateToken(registeredUser.getUsername());
    }

    /**
     * Fetches user details by username.
     *
     * @param username The username to search for.
     * @return An Optional containing the user if found, or empty otherwise.
     */
    public Optional<UserEntity> findByUsername(String username) {
        logger.info("Fetching user: {}", username);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            logger.info("User found with ID: {}", user.get().getId());
        } else {
            logger.warn("User not found: {}", username);
        }
        return user;
    }

    /**
     * Authenticates a user by checking the provided username and password.
     *
     * @param username The username provided by the user.
     * @param rawPassword The password provided by the user.
     * @return true if authentication is successful, false otherwise.
     */
    public String login(String username, String rawPassword) {
        logger.info("Authentication attempting: {}", username);

        Optional<UserEntity> optionalUser = findByUsername(username);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                logger.info("Authentication successful for: {}", username);
                String token = jwtUtil.generateToken(username);
                System.out.println("token = " + token);
                return jwtUtil.generateToken(username);
            } else {
                logger.warn("Invalid password for: {}", username);
                return null;
            }
        } else {
            logger.warn("No user found: {}", username);
            return null;
        }
    }
}
