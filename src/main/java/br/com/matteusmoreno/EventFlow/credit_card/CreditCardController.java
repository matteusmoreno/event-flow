package br.com.matteusmoreno.EventFlow.credit_card;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreditCardDetailsResponse> create(@RequestBody @Valid CreateCreditCardRequestDto request, UriComponentsBuilder uriBuilder) {
        CreditCard creditCard = creditCardService.createCreditCard(request);
        URI uri = uriBuilder.path("/credit-cards/create/{id}").buildAndExpand(creditCard.getId()).toUri();

        return ResponseEntity.created(uri).body(new CreditCardDetailsResponse(creditCard));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<CreditCardDetailsResponse> detailsById(@PathVariable Long id) {
        CreditCard creditCard = creditCardService.detailCreditCardById(id);

        return ResponseEntity.ok(new CreditCardDetailsResponse(creditCard));
    }

    @DeleteMapping("/disable/{id}")
    private ResponseEntity<Void> disable(@PathVariable Long id) {
        creditCardService.disableCreditCard(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<CreditCardDetailsResponse> enable(@PathVariable Long id) {
        CreditCard creditCard = creditCardService.enableCreditCard(id);

        return ResponseEntity.ok(new CreditCardDetailsResponse(creditCard));
    }
}
