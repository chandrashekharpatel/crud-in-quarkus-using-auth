package com.arms.authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    public boolean isValidUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

   
}
