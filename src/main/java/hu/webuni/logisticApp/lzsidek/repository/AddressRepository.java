package hu.webuni.logisticApp.lzsidek.repository;

import hu.webuni.logisticApp.lzsidek.model.Address;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAll(Specification<Address> spec, Sort id);

}
