package hu.tomi.logistic.service;

import hu.tomi.logistic.dto.DelayRequestDto;
import hu.tomi.logistic.config.DelayConfig;
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
public class TransportPlanService {

	private final TransportPlanRepository transportPlanRepository;
    private final SectionRepository sectionRepository;
    private final MilestoneRepository milestoneRepository;
    private final DelayConfig delayConfig;

    public TransportPlanService(TransportPlanRepository transportPlanRepository,
                                SectionRepository sectionRepository,
                                MilestoneRepository milestoneRepository,
                                DelayConfig delayConfig) {
        this.transportPlanRepository = transportPlanRepository;
        this.sectionRepository = sectionRepository;
        this.milestoneRepository = milestoneRepository;
        this.delayConfig = delayConfig;
    }


    @Transactional(readOnly = true)
    public List<TransportPlan> getAll() {
        return transportPlanRepository.findAll();
    }

    @Transactional(readOnly = true)
    public TransportPlan getById(Long id) {
        return transportPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TransportPlan not found with id: " + id));
    }

    @Transactional
    public TransportPlan save(TransportPlan plan) {
        return transportPlanRepository.save(plan);
    }

    @Transactional
    public void updateExpectedIncome(Long id, int newExpectedIncome) {
        TransportPlan plan = transportPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TransportPlan not found with id: " + id));

        plan.setExpectedIncome(newExpectedIncome);
        transportPlanRepository.save(plan);
        transportPlanRepository.flush();
    }

    @Transactional
    public void delete(Long id) {
    	transportPlanRepository.deleteById(id);
    }

    @Transactional
    public void addSectionToPlan(Long planId, Section section) {
        TransportPlan plan = transportPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("TransportPlan not found with id: " + planId));
        section.setTransportPlan(plan);
        sectionRepository.save(section);
    }
    
    @Transactional
    public void registerDelay(Long transportPlanId, DelayRequestDto dto) {
        TransportPlan plan = transportPlanRepository.findById(transportPlanId)
                .orElseThrow(() -> new RuntimeException("TransportPlan not found"));

        Milestone milestone = milestoneRepository.findById(dto.getMilestoneId())
                .orElseThrow(() -> new RuntimeException("Milestone not found"));

        List<Section> sections = sectionRepository.findByTransportPlanId(transportPlanId);

        boolean isPartOfPlan = sections.stream()
                .anyMatch(s -> s.getFromMilestone().getId().equals(dto.getMilestoneId())
                        || s.getToMilestone().getId().equals(dto.getMilestoneId()));

        if (!isPartOfPlan) {
            throw new IllegalArgumentException("Milestone not part of transport plan");
        }

        // Delay current milestone
        milestone.setPlannedTime(milestone.getPlannedTime().plusMinutes(dto.getDelayInMinutes()));
        milestoneRepository.save(milestone);

        // Delay dependent milestones
        sections.stream()
                .filter(s -> s.getFromMilestone().getId().equals(dto.getMilestoneId()))
                .findFirst()
                .ifPresent(section -> {
                    Milestone to = section.getToMilestone();
                    to.setPlannedTime(to.getPlannedTime().plusMinutes(dto.getDelayInMinutes()));
                    milestoneRepository.save(to);
                });

        sections.stream()
                .filter(s -> s.getToMilestone().getId().equals(dto.getMilestoneId()))
                .findFirst()
                .ifPresent(section -> {
                    sectionRepository.findByTransportPlanIdAndNumber(transportPlanId, section.getNumber() + 1)
                            .ifPresent(next -> {
                                Milestone startOfNext = next.getFromMilestone();
                                startOfNext.setPlannedTime(startOfNext.getPlannedTime().plusMinutes(dto.getDelayInMinutes()));
                                milestoneRepository.save(startOfNext);
                            });
                });

        // Update expected income based on delay
        int delay = dto.getDelayInMinutes();
        int decreasePercent = 0;
        for (int i = 0; i < delayConfig.getThresholds().size(); i++) {
            if (delay >= delayConfig.getThresholds().get(i)) {
                decreasePercent = delayConfig.getPercentages().get(i);
            }
        }

        if (decreasePercent > 0) {
            int reducedIncome = plan.getExpectedIncome() * (100 - decreasePercent) / 100;
            updateExpectedIncome(transportPlanId, reducedIncome);
        }
    }
}

