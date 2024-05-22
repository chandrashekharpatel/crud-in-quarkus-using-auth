package com.arms.image.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

import com.arms.image.model.PetRequestDTO;
import com.arms.image.model.PetResponseDTO;
import jakarta.persistence.Query;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PetImageRepository implements PanacheRepository<PetRequestDTO>{

    public PetRequestDTO findByName(String fileName) {
        return find("fileName", fileName).firstResult();
    }
    
    // @Transactional
    // public List<PetRequestDTO> getAllImages() {
    //     return listAll();
    // }

    public PetRequestDTO findByNames(String fileName) {
        return find("fileName", fileName).firstResult();
    }

      @Transactional
    public List<PetResponseDTO> getAllImages() {
        String jpql = "SELECT NEW com.arms.image.model.PetResponseDTO(id, fileName, fileExtension, file) FROM PetRequestDTO";
        Query query = getEntityManager().createQuery(jpql, PetResponseDTO.class);
        return query.getResultList();
    }
}
