package com.arms.citizen;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class  CitizenRepository implements PanacheRepository<Citizen> {

   @Transactional
    public Citizen findByIdWithSimCards(Long id) {
        return find("id", id).firstResult();
}
}

