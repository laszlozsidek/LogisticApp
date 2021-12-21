package hu.webuni.logisticApp.lzsidek.service;

import hu.webuni.logisticApp.lzsidek.model.Address;
import hu.webuni.logisticApp.lzsidek.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Transactional
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    @Transactional
    public void deleteAddressById(Long id) {
        addressRepository.deleteById(id);
    }
}
