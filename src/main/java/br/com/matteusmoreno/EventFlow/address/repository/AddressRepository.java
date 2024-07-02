package br.com.matteusmoreno.EventFlow.address.repository;

import br.com.matteusmoreno.EventFlow.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    boolean existsByZipcode(String zipcode);

    Address findByZipcode(String zipcode);
}
