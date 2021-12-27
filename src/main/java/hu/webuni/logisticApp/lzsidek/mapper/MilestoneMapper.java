package hu.webuni.logisticApp.lzsidek.mapper;

import hu.webuni.logisticApp.lzsidek.dto.MilestoneDto;
import hu.webuni.logisticApp.lzsidek.model.Milestone;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MilestoneMapper {

    List<MilestoneDto> milestonesToDTOs(List<Milestone> milestones);

}