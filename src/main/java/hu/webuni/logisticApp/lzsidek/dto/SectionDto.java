package hu.webuni.logisticApp.lzsidek.dto;

public class SectionDto {
    private Long id;
    private MilestoneDto fromMilestoneDto;
    private MilestoneDto toMilestoneDto;
    private int number;
    private TransportPlanDto transportPlanDto;

    public SectionDto() {
    }

    public SectionDto(Long id, MilestoneDto fromMilestoneDto, MilestoneDto toMilestoneDto, int number, TransportPlanDto transportPlanDto) {
        this.id = id;
        this.fromMilestoneDto = fromMilestoneDto;
        this.toMilestoneDto = toMilestoneDto;
        this.number = number;
        this.transportPlanDto = transportPlanDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MilestoneDto getFromMilestone() {
        return fromMilestoneDto;
    }

    public void setFromMilestone(MilestoneDto fromMilestoneDto) {
        this.fromMilestoneDto = fromMilestoneDto;
    }

    public MilestoneDto getToMilestone() {
        return toMilestoneDto;
    }

    public void setToMilestone(MilestoneDto toMilestoneDto) {
        this.toMilestoneDto = toMilestoneDto;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TransportPlanDto getTransportPlan() {
        return transportPlanDto;
    }

    public void setTransportPlan(TransportPlanDto transportPlanDto) {
        this.transportPlanDto = transportPlanDto;
    }
}
