package com.arms.simcard;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.arms.citizen.Citizen;
import com.arms.citizen.CitizenRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

@ApplicationScoped
public class SIMCardService {
  
    @Inject
    SIMCardRepository simCardRepository; 

    @Inject
    CitizenRepository citizenRepository; // Assuming you have a CitizenRepository
    // @Transactional
    // public void addSIMCard(SIMCard simCard, Long citizenId) {
    //     Citizen citizen = citizenRepository.findById(citizenId);
    //     if (citizen != null) {
    //         simCard.setCitizen(citizen);
    //         simCardRepository.persist(simCard);
    //     } else {
    //         // Handle case where citizen with given ID is not found
    //         // Throw an exception or return an appropriate response
    //     }
        @Transactional
        public void addSimCards(SIMCard simCard){
            simCardRepository.persist(simCard);

        }
        @Transactional
        public void addSIMCard(SIMCard simCard, Long citizenId) {
            Citizen citizen = citizenRepository.findById(citizenId);
            if (citizen != null) {
                simCard.setCitizen(citizen);
                simCardRepository.persist(simCard);
            } else {
                throw new IllegalArgumentException("Citizen with ID " + citizenId + " not found.");
            }
        }
        @Transactional
        public void updateSIMCard(SIMCard simCard, Long citizenId, Long simCardId) {
            // Find the SIMCard entity by ID
            SIMCard existingSIMCard = simCardRepository.findById(simCardId);
            if (existingSIMCard == null) {
                throw new EntityNotFoundException("SIMCard not found with ID: " + simCardId);
            }
            
            // Check if the citizen ID matches the citizen associated with the existing SIMCard
            if (!existingSIMCard.getCitizen().getId().equals(citizenId)) {
                throw new IllegalArgumentException("SIMCard with ID " + simCardId + " is not associated with citizen ID " + citizenId);
            }
            
            // Update the SIMCard properties
            existingSIMCard.setNumber(simCard.getNumber());
            existingSIMCard.setProvider(simCard.getProvider());
            existingSIMCard.setCitizen(simCard.getCitizen());
            existingSIMCard.setActive(simCard.isActive());
            // Update other properties as needed
            
            // Persist the changes
            
        }
         
    public List<SIMCardResponseDTO> getAllSimcards() {
    List<SIMCard> simcards = simCardRepository.listAll(); // Assuming you have a findAll method

    return simcards.stream()
                  .map(simcard -> {
                      SIMCardResponseDTO dto = new SIMCardResponseDTO();
                      dto.setId(simcard.getId());
                      dto.setNumber(simcard.getNumber());
                      dto.setProvider(simcard.getProvider());
                      dto.setCitizenId(simcard.getCitizen().getId()); // Set citizen ID
                      return dto;
                  })
                  .collect(Collectors.toList());
}

}



