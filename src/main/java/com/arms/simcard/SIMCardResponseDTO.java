package com.arms.simcard;

public class SIMCardResponseDTO {
    private Long id;
    private String number;
    private String provider;
    private Long citizenId; // Include citizen ID

    public SIMCardResponseDTO() {
        // Initialize any default values if needed
    }

    // Constructor
    public SIMCardResponseDTO(Long id, String number, String provider, Long citizenId) {
        this.id = id;
        this.number = number;
        this.provider = provider;
        this.citizenId = citizenId;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getProvider() {
        return provider;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
    }
}
