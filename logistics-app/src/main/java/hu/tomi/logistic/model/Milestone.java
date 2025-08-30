package hu.tomi.logistic.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Milestone {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime plannedTime;

    @ManyToOne
    private Address address;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getPlannedTime() { return plannedTime; }
    public void setPlannedTime(LocalDateTime plannedTime) { this.plannedTime = plannedTime; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}
