package hu.webuni.logisticApp.lzsidek.dto;

public class TransportPlanBodyDto {
    private Long milestoneId;
    private int delayInMinutes;

    public TransportPlanBodyDto() {
    }

    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }

    public int getDelayInMinutes() {
        return delayInMinutes;
    }

    public void setDelayInMinutes(int delayInMinutes) {
        this.delayInMinutes = delayInMinutes;
    }
}