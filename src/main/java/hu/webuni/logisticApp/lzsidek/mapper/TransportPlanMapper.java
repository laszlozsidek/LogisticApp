package hu.webuni.logisticApp.lzsidek.mapper;

import hu.webuni.logisticApp.lzsidek.dto.SectionDto;
import hu.webuni.logisticApp.lzsidek.dto.TransportPlanDto;
import hu.webuni.logisticApp.lzsidek.model.Section;
import hu.webuni.logisticApp.lzsidek.model.TransportPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransportPlanMapper {

    TransportPlan DTOToTransportPlan(TransportPlanDto transportPlanDto);

    TransportPlanDto transportPlanToDTO(TransportPlan transportPlan);

    @Mapping(target = "transportPlan", ignore = true)
    SectionDto sectionToDTO(Section section);

}