package hu.webuni.logisticApp.lzsidek.dto;

import java.time.LocalDateTime;

public class MilestoneDto {
    private Long id;
    private AddressDto addressDto;
    private LocalDateTime plannedTime;

    public MilestoneDto() {
    }

    public MilestoneDto(Long id, AddressDto addressDto, LocalDateTime plannedTime) {
        this.id = id;
        this.addressDto = addressDto;
        this.plannedTime = plannedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressDto getAddress() {
        return addressDto;
    }

    public void setAddress(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    public LocalDateTime getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(LocalDateTime plannedTime) {
        this.plannedTime = plannedTime;
    }
}
