package com.arms.simcard;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SIMCardRepository implements PanacheRepository<SIMCard> {
}

