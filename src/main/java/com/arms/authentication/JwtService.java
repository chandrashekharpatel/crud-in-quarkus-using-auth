package com.arms.authentication;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.smallrye.jwt.build.JwtEncryptionBuilder;
import io.smallrye.jwt.build.JwtSignatureBuilder;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    public String generateToken(String username, Set<String> roles) {
        JwtClaimsBuilder claims = Jwt.claims();
        claims.subject(username);
        claims.groups(roles);

        return claims.jws().signatureKeyId("myKey").sign(); // You need to sign the token with your key
    }

    public boolean validateToken(String token) {
        // Implement token validation logic here
        return true; // Placeholder implementation, replace with actual validation logic
    }

    
}
