package hu.webuni.logisticApp.lzsidek.repository;

import hu.webuni.logisticApp.lzsidek.model.Section;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("SELECT s FROM section WHERE s.transportPlan.id = :transportPlanId ORDER BY number")
    List<Section> findByTransportPlanId(@Param("transportPlanId") Long transportPlanId);

    @Query("SELECT s FROM section WHERE s.transportPlan.id = :transportPlanId AND s.number = :number")
    Optional<Section> findNextByTransportPlanIdAndNumber(@Param("transportPlanId") Long transportPlanId, @Param("number") Integer number);

}