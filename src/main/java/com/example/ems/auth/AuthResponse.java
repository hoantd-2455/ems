package com.example.ems.auth;

public class AuthResponse {

    private String token;
    private String userName;
    private String role;

    public AuthResponse(String token, String userName, String role) {
        this.token = token;
        this.userName = userName;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }
}
