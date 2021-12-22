package hu.webuni.logisticApp.lzsidek.web;

import hu.webuni.logisticApp.lzsidek.dto.AddressDto;
import hu.webuni.logisticApp.lzsidek.mapper.AddressMapper;
import hu.webuni.logisticApp.lzsidek.model.Address;
import hu.webuni.logisticApp.lzsidek.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(AddressController.class));

    @Autowired
    AddressService addressService;

    @Autowired
    AddressMapper addressMapper;

    @GetMapping
    public List<AddressDto> getAddresses() {
        return addressMapper.addressesToDTOs(addressService.getAddresses());
    }

    @GetMapping("/{id}")
    public AddressDto getAddressById(@PathVariable Long id) {
        return addressMapper.addressToDTO(getAddressByIdWithException(id));
    }

    @PostMapping
    public AddressDto saveAddress(@Valid @RequestBody AddressDto addressDto) {
        if (addressDto.getId() == null) {
            return addressMapper.addressToDTO(addressService.saveAddress(addressMapper.DTOToAddress(addressDto)));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search")
    public List<AddressDto> findByExample(@RequestBody AddressDto exampleDto) {
        return addressMapper.addressesToDTOs(addressService.findAddressesByExample(addressMapper.DTOToAddress(exampleDto)));
    }

    @PutMapping("/{id}")
    public AddressDto editAddress(@PathVariable Long id, @Valid @RequestBody AddressDto modifiedAddressDto) {
        if (!Objects.equals(id, modifiedAddressDto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Address modifiedAddress = null;
        if (getAddressByIdWithException(id) != null) {
            modifiedAddress = addressMapper.DTOToAddress(modifiedAddressDto);
        }
        return addressMapper.addressToDTO(addressService.saveAddress(modifiedAddress));
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        try {
            addressService.deleteAddressById(id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Address with id " + id + " not found in db, 200 OK sent to client");
        }
    }

    private Address getAddressByIdWithException(Long id) {
        return addressService.getAddressById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
