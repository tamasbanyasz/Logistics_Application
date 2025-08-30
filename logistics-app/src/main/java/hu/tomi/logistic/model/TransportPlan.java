package hu.tomi.logistic.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TransportPlan {

    @Id
    @GeneratedValue
    private Long id;

    private Integer expectedIncome;

    @OneToMany(mappedBy = "transportPlan", cascade = CascadeType.ALL)
    private List<Section> sections = new ArrayList<>();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getExpectedIncome() { return expectedIncome; }
    public void setExpectedIncome(Integer expectedIncome) { this.expectedIncome = expectedIncome; }

    public List<Section> getSections() { return sections; }
    public void setSections(List<Section> sections) { this.sections = sections; }
}
