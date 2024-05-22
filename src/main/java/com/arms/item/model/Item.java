package com.arms.item.model;
import java.util.List;
import com.arms.product.model.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    // @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Product> products;

    // Default constructor required by JPA
    public Item() {}

    // Getter and setter methods for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and setter methods for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter methods for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

