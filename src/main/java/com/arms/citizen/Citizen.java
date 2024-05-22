package com.arms.citizen;

import java.util.List;

import com.arms.simcard.SIMCard;

import jakarta.persistence.*;

@Entity
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;
    // @OneToMany(mappedBy = "citizen" ,fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    // List<SIMCard> simCard;

    public Citizen() {
    }

    public Citizen(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    // public List<SIMCard> getSimCards() {
    //     return simCard;
    // }

    // public void setSimCards(List<SIMCard> simCards) {
    //     this.simCard = simCards;
    // }
}
