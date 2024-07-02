package br.com.matteusmoreno.EventFlow.event.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateEventRequestDto(
        @NotNull(message = "Id is required")
        UUID id,
        String name,
        String description,
        @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Invalid zipcode format. Use XXXXX-XXX")
        String zipcode,
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "Invalid date format. Use YYYY-MM-DDTHH:mm:ss")
        LocalDateTime date,
        BigDecimal price,
        Integer ticketAvailable) {
}
