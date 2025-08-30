package hu.tomi.logistic.dto;

public class SectionDto {
    private Long id;
    private Integer number;
    private Long fromMilestoneId;
    private Long toMilestoneId;
    private Long transportPlanId;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public Long getFromMilestoneId() { return fromMilestoneId; }
    public void setFromMilestoneId(Long fromMilestoneId) { this.fromMilestoneId = fromMilestoneId; }

    public Long getToMilestoneId() { return toMilestoneId; }
    public void setToMilestoneId(Long toMilestoneId) { this.toMilestoneId = toMilestoneId; }

    public Long getTransportPlanId() { return transportPlanId; }
    public void setTransportPlanId(Long transportPlanId) { this.transportPlanId = transportPlanId; }
}

