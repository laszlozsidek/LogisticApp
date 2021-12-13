package hu.webuni.logisticApp.lzsidek.model;

import javax.persistence.*;

@Entity
public class Section {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Milestone fromMilestone;
    @ManyToOne
    private Milestone toMilestone;
    private int number;
    @ManyToOne
    private TransportPlan transportPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Milestone getFromMilestone() {
        return fromMilestone;
    }

    public void setFromMilestone(Milestone fromMilestone) {
        this.fromMilestone = fromMilestone;
    }

    public Milestone getToMilestone() {
        return toMilestone;
    }

    public void setToMilestone(Milestone toMilestone) {
        this.toMilestone = toMilestone;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TransportPlan getTransportPlan() {
        return transportPlan;
    }

    public void setTransportPlan(TransportPlan transportPlan) {
        this.transportPlan = transportPlan;
    }
}
