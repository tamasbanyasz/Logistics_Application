package hu.tomi.logistic.service;

import hu.tomi.logistic.model.Milestone;
import hu.tomi.logistic.model.Section;
import hu.tomi.logistic.model.TransportPlan;
import hu.tomi.logistic.repository.MilestoneRepository;
import hu.tomi.logistic.repository.SectionRepository;
import hu.tomi.logistic.repository.TransportPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final MilestoneRepository milestoneRepository;
    private final TransportPlanRepository transportPlanRepository;

    public SectionService(SectionRepository sectionRepository,
                          MilestoneRepository milestoneRepository,
                          TransportPlanRepository transportPlanRepository) {
        this.sectionRepository = sectionRepository;
        this.milestoneRepository = milestoneRepository;
        this.transportPlanRepository = transportPlanRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Section> getAll() {
        return sectionRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Section getById(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Section not found with id: " + id));
    }
    
    @Transactional
    public Section save(Section section, Long fromMilestoneId, Long toMilestoneId, Long transportPlanId) {
        Milestone fromMilestone = milestoneRepository.findById(fromMilestoneId)
                .orElseThrow(() -> new RuntimeException("FromMilestone not found with id: " + fromMilestoneId));

        Milestone toMilestone = milestoneRepository.findById(toMilestoneId)
                .orElseThrow(() -> new RuntimeException("ToMilestone not found with id: " + toMilestoneId));

        TransportPlan transportPlan = transportPlanRepository.findById(transportPlanId)
                .orElseThrow(() -> new RuntimeException("TransportPlan not found with id: " + transportPlanId));

        section.setFromMilestone(fromMilestone);
        section.setToMilestone(toMilestone);
        section.setTransportPlan(transportPlan);

        return sectionRepository.save(section);
    }
    
    @Transactional
    public void delete(Long id) {
        sectionRepository.deleteById(id);
    }
}
