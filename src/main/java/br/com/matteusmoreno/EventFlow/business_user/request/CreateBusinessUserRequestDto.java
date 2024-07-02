package br.com.matteusmoreno.EventFlow.business_user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateBusinessUserRequestDto(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "\\(\\d{2}\\)\\d{5}-\\d{4}", message = "Phone number format (00)000000000")
        String phone,
        @NotBlank(message = "Zipcode is required")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "Zipcode format 00000-000")
        String zipcode,
        @NotBlank(message = "Email is required")
        @Email(message = "Email format invalid")
        String email,
        String password) {
}
