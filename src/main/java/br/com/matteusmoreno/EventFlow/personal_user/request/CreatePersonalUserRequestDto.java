package br.com.matteusmoreno.EventFlow.personal_user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CreatePersonalUserRequestDto(
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "Birth date is required")
        LocalDate birthDate,
        @Pattern(regexp = "\\(\\d{2}\\)\\d{5}-\\d{4}", message = "Phone number format (00)000000000")
        String phone,
        @NotBlank(message = "Zip code is required")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "Zip code format 00000-000")
        String zipcode,
        @NotBlank(message = "Email is required")
        @Email(message = "Email format invalid")
        String email,
        @NotBlank(message = "Password is required")
        String password) {
}
