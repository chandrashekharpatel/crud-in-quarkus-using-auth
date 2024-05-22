package com.arms.simcard;

import com.arms.citizen.Citizen;

import jakarta.persistence.*;

@Entity
public class SIMCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String provider;
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    // Constructors
    public SIMCard() {
    }
    

    public SIMCard(String number, String provider, boolean isActive) {
        this.number = number;
        this.provider = provider;
        this.isActive = isActive;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }
}

