package br.com.matteusmoreno.EventFlow.personal_user.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateBusinessUserRequestDto(
        @NotNull(message = "Id is required")
        UUID id,
        String name,
        String phone,
        String zipcode,
        String email,
        String password) {
}
