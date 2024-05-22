package com.arms.product.controller;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import com.arms.product.model.Product;
import com.arms.product.repository.ProductRepository;
import com.arms.product.service.ProductService;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductRepository productRepository;
    @Inject
    ProductService productService;

    @GET
    public List<Product> getAllProducts() {
        return productRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Product getProductById(@PathParam("id") Long id) {
        return productRepository.findById(id);
    }

    @POST
    @Transactional
    public void addProduct(Product product) {
        productRepository.persist(product);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public void updateProduct(@PathParam("id") Long id, Product updatedProduct) {
        // Retrieve the existing product by ID
        Product existingProduct = productRepository.findById(id);
        if (existingProduct != null) {
            // Update the attributes of the existing product
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setItemId(updatedProduct.getItemId());
            
            // Call the repository's update method
            productRepository.persist(existingProduct);
        } else {
            // Handle the case where the product with the given ID is not found
            throw new NotFoundException("Product with ID " + id + " not found");
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteProduct(@PathParam("id") Long id) {
        productRepository.deleteById(id);
    }

    //   @POST
    // public Response index(Product roduct) throws IOException {
    //     if (product.productId == null) {
    //         product.productId = UUID.randomUUID().toString();
    //     }
    //     productService.index(product);
    //     return Response.created(URI.create("/product/" + product.id)).build();
    // }

    // @GET
    // @Path("/{id}")
    // public Product get(String id) throws IOException {
    //     return productService.get(id);
    // }

    // @GET
    // @Path("/search")
    // public List<Product> search(@RestQuery String name, @RestQuery String color) throws IOException {
    //     if (name != null) {
    //         return productService.searchByName(name);
    //     } else {
    //         throw new BadRequestException("Should provide name or color query parameter");
    //     }
    // }
}
