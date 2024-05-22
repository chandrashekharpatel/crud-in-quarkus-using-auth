package com.arms.citizen;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CitizenService {

    @Inject
    CitizenRepository citizenRepository; // Inject the repository to access Citizen entities

    // Method to get a Citizen entity by its ID
    public Citizen getCitizenById(Long id) {
        return citizenRepository.findById(id);
    }

    // Additional methods for citizen-related operations can be added here
}
