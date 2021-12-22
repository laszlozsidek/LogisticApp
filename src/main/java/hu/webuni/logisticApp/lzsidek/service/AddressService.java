package hu.webuni.logisticApp.lzsidek.service;

import hu.webuni.logisticApp.lzsidek.model.Address;
import hu.webuni.logisticApp.lzsidek.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    public List<Address> findAddressesByExample(Address exampleDto) {
        String countryCode = exampleDto.getCountryCode();
        String city = exampleDto.getCity();
        String postalCode = exampleDto.getPostalCode();
        String street = exampleDto.getStreet();

        Specification<Address> spec = Specification.where(null);

        if (StringUtils.hasText(countryCode)) {
            spec = spec.and(AddressSpecification.hasCountryCode(countryCode));
        }

        if (StringUtils.hasText(city)) {
            spec = spec.and(AddressSpecification.hasCity(city));
        }

        if (StringUtils.hasText(postalCode)) {
            spec = spec.and(AddressSpecification.hasPostalCode(postalCode));
        }

        if (StringUtils.hasText(street)) {
            spec = spec.and(AddressSpecification.hasStreet(street));
        }

        return addressRepository.findAll(spec, Sort.by("id"));
    }
}