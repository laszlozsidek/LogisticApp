package hu.webuni.logisticApp.lzsidek.mapper;

import hu.webuni.logisticApp.lzsidek.dto.AddressDto;
import hu.webuni.logisticApp.lzsidek.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address DTOToAddress(AddressDto addressDTO);

    AddressDto addressToDTO(Address saveAddress);
}
