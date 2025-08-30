package hu.tomi.logistic.dto;

import java.time.LocalDateTime;

public class MilestoneDto {
    private Long id;
    private LocalDateTime plannedTime;
    private Long addressId;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getPlannedTime() { return plannedTime; }
    public void setPlannedTime(LocalDateTime plannedTime) { this.plannedTime = plannedTime; }

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }
}
