package br.com.matteusmoreno.EventFlow.address.service;

import br.com.matteusmoreno.EventFlow.address.entity.Address;
import br.com.matteusmoreno.EventFlow.address.mapper.AddressMapper;
import br.com.matteusmoreno.EventFlow.address.repository.AddressRepository;
import br.com.matteusmoreno.EventFlow.address.response.AddressResponseDto;
import br.com.matteusmoreno.EventFlow.client.ViaCepClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    private final ViaCepClient viaCepClient;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(ViaCepClient viaCepClient, AddressMapper addressMapper, AddressRepository addressRepository) {
        this.viaCepClient = viaCepClient;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
    }
    @Transactional
    public Address createAddress(String zipcode) {
        if (addressRepository.existsByZipcode(zipcode)) {
            return addressRepository.findByZipcode(zipcode);
        } else {
            AddressResponseDto response = viaCepClient.getAddressByCep(zipcode);
            Address address = addressMapper.toEntity(response);
            addressRepository.save(address);
            return address;
        }
    }
}
