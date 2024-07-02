package br.com.matteusmoreno.EventFlow.personal_user.response;

import br.com.matteusmoreno.EventFlow.address.entity.Address;
import br.com.matteusmoreno.EventFlow.personal_user.entity.PersonalUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PersonalCustomerDetailsResponseDto(
        UUID id,
        String name,
        LocalDate birthDate,
        String phone,
        Address address,
        String email,
        String password,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Boolean active) {

    public PersonalCustomerDetailsResponseDto(PersonalUser personalUser) {
        this(
                personalUser.getId(),
                personalUser.getName(),
                personalUser.getBirthDate(),
                personalUser.getPhone(),
                personalUser.getAddress(),
                personalUser.getEmail(),
                personalUser.getPassword(),
                personalUser.getCreatedAt(),
                personalUser.getUpdatedAt(),
                personalUser.getDeletedAt(),
                personalUser.getActive()
        );
    }
}
