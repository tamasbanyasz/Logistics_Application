package hu.tomi.logistic.model;

import jakarta.persistence.*;

@Entity
public class Section {

    @Id
    @GeneratedValue
    private Long id;

    private Integer number;

    @ManyToOne
    private Milestone fromMilestone;

    @ManyToOne
    private Milestone toMilestone;

    @ManyToOne
    private TransportPlan transportPlan;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public Milestone getFromMilestone() { return fromMilestone; }
    public void setFromMilestone(Milestone fromMilestone) { this.fromMilestone = fromMilestone; }

    public Milestone getToMilestone() { return toMilestone; }
    public void setToMilestone(Milestone toMilestone) { this.toMilestone = toMilestone; }

    public TransportPlan getTransportPlan() { return transportPlan; }
    public void setTransportPlan(TransportPlan transportPlan) { this.transportPlan = transportPlan; }
}
