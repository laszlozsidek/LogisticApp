package hu.webuni.logisticApp.lzsidek.model;

import javax.persistence.*;
import java.util.List;

//@NamedEntityGraph(
//        name = "tpSections",
//        attributeNodes = @NamedAttributeNode("sections")
//)
@Entity
public class TransportPlan {
    @Id
    @GeneratedValue
    private Long id;
    private double plannedIncome;
    @OneToMany(mappedBy = "transportPlan")
    private List<Section> sections;

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

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}