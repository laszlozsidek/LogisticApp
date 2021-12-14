package hu.webuni.logisticApp.lzsidek.dto;

import java.util.ArrayList;
import java.util.List;

public class TransportPlanDto {
    private Long id;
    private boolean isPlanFulfilled;
    private int plannedIncome;
    private List<SectionDto> sectionDTOs = new ArrayList<>();

    public TransportPlanDto() {
    }

    public TransportPlanDto(Long id, boolean isPlanFulfilled, int plannedIncome, List<SectionDto> sectionDTOs) {
        this.id = id;
        this.isPlanFulfilled = isPlanFulfilled;
        this.plannedIncome = plannedIncome;
        this.sectionDTOs = sectionDTOs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPlanFulfilled() {
        return isPlanFulfilled;
    }

    public void setPlanFulfilled(boolean planFulfilled) {
        isPlanFulfilled = planFulfilled;
    }

    public int getPlannedIncome() {
        return plannedIncome;
    }

    public void setPlannedIncome(int plannedIncome) {
        this.plannedIncome = plannedIncome;
    }

    public List<SectionDto> getSections() {
        return sectionDTOs;
    }

    public void setSections(List<SectionDto> sectionDTOs) {
        this.sectionDTOs = sectionDTOs;
    }
}