package hu.webuni.logisticApp.lzsidek.mapper;

import hu.webuni.logisticApp.lzsidek.dto.AddressDto;
import hu.webuni.logisticApp.lzsidek.model.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address DTOToAddress(AddressDto addressDTO);

    AddressDto addressToDTO(Address saveAddress);

    List<AddressDto> addressesToDTOs(List<Address> addresses);
}