package br.com.matteusmoreno.EventFlow.client;

import br.com.matteusmoreno.EventFlow.address.response.AddressResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

    @GetMapping("/{cep}/json")
    AddressResponseDto getAddressByCep(@PathVariable String cep);
}
