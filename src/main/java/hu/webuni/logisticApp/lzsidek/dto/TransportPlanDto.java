package hu.webuni.logisticApp.lzsidek.dto;

import java.util.ArrayList;
import java.util.List;

public class TransportPlanDto {
    private Long id;
    private double plannedIncome;
    private List<SectionDto> sectionDTOs = new ArrayList<>();

    public TransportPlanDto() {
    }

    public TransportPlanDto(Long id, int plannedIncome, List<SectionDto> sectionDTOs) {
        this.id = id;
        this.plannedIncome = plannedIncome;
        this.sectionDTOs = sectionDTOs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPlannedIncome() {
        return plannedIncome;
    }

    public void setPlannedIncome(double plannedIncome) {
        this.plannedIncome = plannedIncome;
    }

    public List<SectionDto> getSections() {
        return sectionDTOs;
    }

    public void setSections(List<SectionDto> sectionDTOs) {
        this.sectionDTOs = sectionDTOs;
    }
}