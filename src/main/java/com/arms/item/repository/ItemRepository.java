package com.arms.item.repository;

import com.arms.item.model.Item;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
public class ItemRepository implements PanacheRepository<Item> {
}
