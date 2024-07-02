package br.com.matteusmoreno.EventFlow.address.response;

public record AddressResponseDto(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf) {
}
