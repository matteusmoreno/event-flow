package br.com.matteusmoreno.EventFlow.business_user.response;

import br.com.matteusmoreno.EventFlow.address.entity.Address;
import br.com.matteusmoreno.EventFlow.business_user.entity.BusinessUser;

import java.time.LocalDateTime;
import java.util.UUID;

public record BusinessUserDetailsResponseDto(
        UUID id,
        String name,
        String phone,
        Address address,
        String email,
        String password,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Boolean active) {

    public BusinessUserDetailsResponseDto(BusinessUser businessUser) {
        this(
                businessUser.getId(),
                businessUser.getName(),
                businessUser.getPhone(),
                businessUser.getAddress(),
                businessUser.getEmail(),
                businessUser.getPassword(),
                businessUser.getCreatedAt(),
                businessUser.getUpdatedAt(),
                businessUser.getDeletedAt(),
                businessUser.getActive());

            }
}
