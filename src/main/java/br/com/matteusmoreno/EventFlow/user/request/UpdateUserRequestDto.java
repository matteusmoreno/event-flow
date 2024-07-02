package br.com.matteusmoreno.EventFlow.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateUserRequestDto(
        @NotNull(message = "Id is required")
        UUID id,
        String name,
        LocalDate birthDate,
        @Pattern(regexp = "\\(\\d{2}\\)\\d{5}-\\d{4}", message = "Phone number format (00)000000000")
        String phone,
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "Zip code format 00000-000")
        String zipcode,
        @Email(message = "Email format invalid")
        String email,
        String password) {
}
