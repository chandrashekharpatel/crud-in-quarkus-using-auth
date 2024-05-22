package com.arms.authentication;

public  class AuthResponse {
    private String token;
    
    // constructor and getter
    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
