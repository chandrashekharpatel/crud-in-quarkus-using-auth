package com.arms.item.service;
import com.arms.item.model.Item;
import com.arms.item.repository.ItemRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ItemService {

    @Inject
    ItemRepository itemRepository;

    public List<Item> searchItems(String query) {
        List<Item> allItems = itemRepository.listAll(); // Fetch all items from the repository
        List<Item> matchedItems = new ArrayList<>(); // List to store matched items

        // Iterate through all items and check if their name or description contains the query
        for (Item item : allItems) {
            if (item.getName().toLowerCase().contains(query.toLowerCase()) ||
                item.getDescription().toLowerCase().contains(query.toLowerCase())) {
                matchedItems.add(item); // If matched, add the item to the list
            }
        }

        return matchedItems; // Return the list of matched items
    }
}

