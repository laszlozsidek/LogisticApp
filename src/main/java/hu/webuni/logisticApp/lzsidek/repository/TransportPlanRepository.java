package hu.webuni.logisticApp.lzsidek.repository;

import hu.webuni.logisticApp.lzsidek.model.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {

    @Override
    @Query("SELECT DISTINCT t FROM TransportPlan t LEFT JOIN FETCH t.sections WHERE t.id = :transportPlanId")
    Optional<TransportPlan> findById(@Param("transportPlanId") Long transportPlanId);

//    @Override
//    @EntityGraph("tp.with.sections")
//    TransportPlan save(TransportPlan entity);
}