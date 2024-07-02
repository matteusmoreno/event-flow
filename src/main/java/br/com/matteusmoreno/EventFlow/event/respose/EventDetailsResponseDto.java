package br.com.matteusmoreno.EventFlow.event.respose;

import br.com.matteusmoreno.EventFlow.address.entity.Address;
import br.com.matteusmoreno.EventFlow.event.entity.Event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventDetailsResponseDto(
        UUID id,
        String name,
        String description,
        Address address,
        LocalDateTime date,
        BigDecimal price,
        Integer ticketAvailable,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Boolean active) {

    public EventDetailsResponseDto(Event event) {
        this(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getAddress(),
                event.getDate(),
                event.getPrice(),
                event.getTicketAvailable(),
                event.getCreatedAt(),
                event.getUpdatedAt(),
                event.getDeletedAt(),
                event.getActive()
        );
    }
}
