package hu.tomi.logistic.dto;

public class DelayRequestDto {
    private Long milestoneId;
    private int delayInMinutes;

    // Getters & setters
    public Long getMilestoneId() { return milestoneId; }
    public void setMilestoneId(Long milestoneId) { this.milestoneId = milestoneId; }

    public int getDelayInMinutes() { return delayInMinutes; }
    public void setDelayInMinutes(int delayInMinutes) { this.delayInMinutes = delayInMinutes; }
}
