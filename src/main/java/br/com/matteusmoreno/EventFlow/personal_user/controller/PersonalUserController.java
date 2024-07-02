package br.com.matteusmoreno.EventFlow.personal_user.controller;

import br.com.matteusmoreno.EventFlow.personal_user.entity.PersonalUser;
import br.com.matteusmoreno.EventFlow.personal_user.request.CreatePersonalUserRequestDto;
import br.com.matteusmoreno.EventFlow.personal_user.request.UpdatePersonalUserRequestDto;
import br.com.matteusmoreno.EventFlow.personal_user.response.PersonalCustomerDetailsResponseDto;
import br.com.matteusmoreno.EventFlow.personal_user.service.PersonalUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/personal-users")
public class PersonalUserController {

    private final PersonalUserService personalUserService;

    @Autowired
    public PersonalUserController(PersonalUserService personalUserService) {
        this.personalUserService = personalUserService;
    }

    @PostMapping("/create")
    public ResponseEntity<PersonalCustomerDetailsResponseDto> create(@RequestBody @Valid CreatePersonalUserRequestDto requestDto, UriComponentsBuilder uriBuilder) {
        PersonalUser personalUser = personalUserService.createPersonalUser(requestDto);
        URI uri = uriBuilder.path("/personal-users/{id}").buildAndExpand(personalUser.getId()).toUri();

        return ResponseEntity.created(uri).body(new PersonalCustomerDetailsResponseDto(personalUser));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<PersonalCustomerDetailsResponseDto> detailsById(@PathVariable UUID id) {
        PersonalUser personalUser = personalUserService.detailPersonalUserById(id);

        return ResponseEntity.ok(new PersonalCustomerDetailsResponseDto(personalUser));
    }

    @PutMapping("/update")
    public ResponseEntity<PersonalCustomerDetailsResponseDto> update(@RequestBody @Valid UpdatePersonalUserRequestDto request) {
        PersonalUser personalUser = personalUserService.updatePersonalUser(request);

        return ResponseEntity.ok(new PersonalCustomerDetailsResponseDto(personalUser));
    }
}
