package hu.webuni.logisticApp.lzsidek.web;

import hu.webuni.logisticApp.lzsidek.dto.AddressDto;
import hu.webuni.logisticApp.lzsidek.mapper.AddressMapper;
import hu.webuni.logisticApp.lzsidek.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    AddressMapper addressMapper;


//    @GetMapping
//    public List<AddressDto> getAddresses() {
//
//    }

    @PostMapping
    public AddressDto saveAddress(@Valid @RequestBody AddressDto addressDto) {
        if (addressDto.getId() == null) {
            return addressMapper.addressToDTO(addressService.saveAddress(addressMapper.DTOToAddress(addressDto)));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
