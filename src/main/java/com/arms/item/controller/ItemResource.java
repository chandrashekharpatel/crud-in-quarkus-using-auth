package com.arms.item.controller;
import java.util.List;

import com.arms.item.model.Item;
import com.arms.item.repository.ItemRepository;
import com.arms.item.service.ItemService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {

    @Inject
    ItemRepository repository;

    @Inject
    ItemService itemService;

    @GET
    public List<Item> getAllItems() {
        return repository.listAll();
    }

    @GET
    @Path("/{id}")
    public Item getItemById(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @POST
    @Transactional
    public void addItem(Item item) {
        repository.persist(item);
    }

@PUT
@Path("/{id}")
@Transactional
public void updateItem(@PathParam("id") Long id, Item updatedItem) {
    Item existingItem = repository.findById(id);
    if (existingItem != null) {
        existingItem.setName(updatedItem.getName());
        existingItem.setDescription(updatedItem.getDescription());
        repository.persist(existingItem);
    } else {
        throw new NotFoundException("Item with ID " + id + " not found");
    }
}


    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteItem(@PathParam("id") Long id) {
        repository.delete("id", id);
    }
    @GET
    @Path("/search")
    public Response searchItems(@QueryParam("query") String query) {
        List<Item> matchedItems = itemService.searchItems(query);
        return Response.ok(matchedItems).build();
    }
}
