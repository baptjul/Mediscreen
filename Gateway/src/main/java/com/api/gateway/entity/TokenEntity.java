package com.api.gateway.entity;

/**
 * This entity is used to receive a JWT token from the frontend
 */
public class TokenEntity {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
