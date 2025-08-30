package hu.tomi.logistic.dto;


import java.util.List;

public class TransportPlanDto {
    private Long id;
    private Integer expectedIncome;
    private List<Long> sectionIds;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getExpectedIncome() { return expectedIncome; }
    public void setExpectedIncome(Integer expectedIncome) { this.expectedIncome = expectedIncome; }

    public List<Long> getSectionIds() { return sectionIds; }
    public void setSectionIds(List<Long> sectionIds) { this.sectionIds = sectionIds; }
}
