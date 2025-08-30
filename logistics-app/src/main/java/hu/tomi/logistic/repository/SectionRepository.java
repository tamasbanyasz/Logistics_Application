package hu.tomi.logistic.repository;

import hu.tomi.logistic.model.Section;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
	
    List<Section> findByTransportPlanId(Long transportPlanId);
    
    Optional<Section> findByTransportPlanIdAndNumber(Long transportPlanId, int number);

}

