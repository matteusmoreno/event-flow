package br.com.matteusmoreno.EventFlow.business_user.controller;

import br.com.matteusmoreno.EventFlow.business_user.entity.BusinessUser;
import br.com.matteusmoreno.EventFlow.business_user.request.CreateBusinessUserRequestDto;
import br.com.matteusmoreno.EventFlow.business_user.response.BusinessUserDetailsResponseDto;
import br.com.matteusmoreno.EventFlow.business_user.service.BusinessUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
}
