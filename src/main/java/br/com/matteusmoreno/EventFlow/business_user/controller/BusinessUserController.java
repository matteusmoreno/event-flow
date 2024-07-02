package br.com.matteusmoreno.EventFlow.business_user.controller;

import br.com.matteusmoreno.EventFlow.business_user.entity.BusinessUser;
import br.com.matteusmoreno.EventFlow.business_user.request.CreateBusinessUserRequestDto;
import br.com.matteusmoreno.EventFlow.business_user.response.BusinessUserDetailsResponseDto;
import br.com.matteusmoreno.EventFlow.business_user.service.BusinessUserService;
import br.com.matteusmoreno.EventFlow.personal_user.request.UpdateBusinessUserRequestDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/business-users")
public class BusinessUserController {

    private final BusinessUserService businessUserService;

    @Autowired
    public BusinessUserController(BusinessUserService businessUserService) {
        this.businessUserService = businessUserService;
    }

    @PostMapping("/create")
    public ResponseEntity<BusinessUserDetailsResponseDto> create(@RequestBody @Valid CreateBusinessUserRequestDto request, UriComponentsBuilder uriBuilder) {
        BusinessUser businessUser = businessUserService.createBusinessUser(request);
        URI uri = uriBuilder.path("/business-users/{id}").buildAndExpand(businessUser.getId()).toUri();

        return ResponseEntity.created(uri).body(new BusinessUserDetailsResponseDto(businessUser));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<BusinessUserDetailsResponseDto> detailsById(@PathVariable UUID id) {
        BusinessUser businessUser = businessUserService.detailBusinessUserById(id);

        return ResponseEntity.ok(new BusinessUserDetailsResponseDto(businessUser));
    }

    @PutMapping("/update")
    public ResponseEntity<BusinessUserDetailsResponseDto> update(@RequestBody @Valid UpdateBusinessUserRequestDto request) {
        BusinessUser businessUser = businessUserService.updateBusinessUser(request);

        return ResponseEntity.ok(new BusinessUserDetailsResponseDto(businessUser));
    }
}
