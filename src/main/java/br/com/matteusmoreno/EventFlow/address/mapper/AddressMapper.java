package br.com.matteusmoreno.EventFlow.address.mapper;

import br.com.matteusmoreno.EventFlow.address.entity.Address;
import br.com.matteusmoreno.EventFlow.address.response.AddressResponseDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressResponseDto response) {
        return Address.builder()
                .zipcode(response.cep())
                .street(response.logradouro())
                .neighborhood(response.bairro())
                .city(response.localidade())
                .state(response.uf())
                .build();
    }
}
