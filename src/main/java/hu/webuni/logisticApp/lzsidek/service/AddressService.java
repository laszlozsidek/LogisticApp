package hu.webuni.logisticApp.lzsidek.service;

import hu.webuni.logisticApp.lzsidek.model.Address;
import hu.webuni.logisticApp.lzsidek.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Transactional
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }
}
