package hu.webuni.logisticApp.lzsidek.service;

import hu.webuni.logisticApp.lzsidek.model.Address;
import hu.webuni.logisticApp.lzsidek.model.Address_;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {

    public static Specification<Address> hasCountryCode(String countryCode) {
        return (root, cq, cb) -> cb.equal(root.get(Address_.countryCode), countryCode);
    }

    public static Specification<Address> hasCity(String city) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.city)), (city + "%").toLowerCase());
    }

    public static Specification<Address> hasPostalCode(String postalCode) {
        return (root, cq, cb) -> cb.equal(root.get(Address_.postalCode), postalCode);
    }

    public static Specification<Address> hasStreet(String street) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.street)), (street + "%").toLowerCase());
    }
}