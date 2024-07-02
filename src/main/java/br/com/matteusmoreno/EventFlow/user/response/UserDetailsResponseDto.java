package br.com.matteusmoreno.EventFlow.user.response;

import br.com.matteusmoreno.EventFlow.address.entity.Address;
import br.com.matteusmoreno.EventFlow.role.Role;
import br.com.matteusmoreno.EventFlow.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserDetailsResponseDto(
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
        Boolean active,
        List<Role> roles) {

    public UserDetailsResponseDto(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getBirthDate(),
                user.getPhone(),
                user.getAddress(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeletedAt(),
                user.getActive(),
                user.getRoles()
        );
    }
}
