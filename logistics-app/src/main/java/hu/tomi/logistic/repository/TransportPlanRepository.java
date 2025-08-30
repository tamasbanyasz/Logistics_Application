package hu.tomi.logistic.repository;


import hu.tomi.logistic.model.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {
}
