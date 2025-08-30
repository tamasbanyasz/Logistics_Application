package hu.tomi.logistic.repository;


import hu.tomi.logistic.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}
