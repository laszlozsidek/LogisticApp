package hu.webuni.logisticApp.lzsidek.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class TransportPlan {
    @Id
    @GeneratedValue
    private Long id;
    private boolean isPlanFulfilled;
    private int plannedIncome;
    @OneToMany(mappedBy = "transportPlan")
    private List<Section> sections;

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

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}