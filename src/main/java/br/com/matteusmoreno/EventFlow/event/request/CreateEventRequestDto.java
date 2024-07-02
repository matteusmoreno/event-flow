package br.com.matteusmoreno.EventFlow.event.request;

import br.com.matteusmoreno.EventFlow.address.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateEventRequestDto(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "Zipcode is required")
        @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Invalid zipcode format. Use XXXXX-XXX")
        String zipcode,
        @NotNull(message = "Date is required")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "Invalid date format. Use YYYY-MM-DDTHH:mm:ss")
        LocalDateTime date,
        @NotNull(message = "Price is required")
        BigDecimal price,
        @NotNull(message = "Ticket available is required")
        Integer ticketAvailable) {
}
