package hu.webuni.logisticApp.lzsidek.mapper;

import hu.webuni.logisticApp.lzsidek.dto.TransportPlanDto;
import hu.webuni.logisticApp.lzsidek.model.TransportPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransportPlanMapper {

    TransportPlan DTOToTransportPlan(TransportPlanDto transportPlanDto);

    TransportPlanDto TransportPlanToDTO(TransportPlan transportPlan);

}