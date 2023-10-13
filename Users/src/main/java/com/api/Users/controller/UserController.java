package com.api.Users.controller;

import com.api.Users.entity.UserEntity;
import com.api.Users.service.UserService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint to register a new user
     *
     * @param user The user details to register
     * @return The registered user
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserEntity user) {
        String registeredUser = userService.signup(user);
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Endpoint to authenticate a user
     *
     * @param user The user details to login
     * @return A response indicating success or failure
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity user) {
        String token = userService.login(user.getUsername(), user.getPassword());
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }
}
