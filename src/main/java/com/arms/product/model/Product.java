package com.arms.product.model;

import com.arms.item.model.Item;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private Long itemId;

    // @ManyToOne
    // private Item item;

    // Constructors
    public Product() {
    }

    // Getters and setters
     public Long getItemId(){
        return itemId;
     }
     public void setItemId(Long itemId){
         this.itemId=itemId;
     }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // public Item getItem() {
    //     return item;
    // }

    // public void setItem(Item item) {
    //     this.item = item;
    // }
}
