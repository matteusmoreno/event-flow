package br.com.matteusmoreno.EventFlow.credit_card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCreditCardRequestDto(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Number is required")
        @Pattern(regexp = "^\\d{16}$", message = "Number must be a 16 digit number")
        String number,
        @NotBlank(message = "CVV is required")
        @Pattern(regexp = "^\\d{3}$", message = "CVV must be a 3 digit number")
        String cvv,
        @NotBlank(message = "Expiration date is required")
        @Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{2}$", message = "Expiration date must be in the format MM/YY")
        String expirationDate) {
}
