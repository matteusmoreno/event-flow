package br.com.matteusmoreno.EventFlow.credit_card;

import java.time.LocalDateTime;

public record CreditCardDetailsResponse(
        Long id,
        String name,
        String number,
        String cvv, String expirationDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Boolean active) {

    public CreditCardDetailsResponse(CreditCard creditCard) {
        this(creditCard.getId(),
                creditCard.getName(),
                creditCard.getNumber(),
                creditCard.getCvv(),
                creditCard.getExpirationDate(),
                creditCard.getCreatedAt(),
                creditCard.getUpdatedAt(),
                creditCard.getDeletedAt(),
                creditCard.getActive());
    }
}
