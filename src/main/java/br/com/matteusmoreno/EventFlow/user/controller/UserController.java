package br.com.matteusmoreno.EventFlow.user.controller;

import br.com.matteusmoreno.EventFlow.user.entity.User;
import br.com.matteusmoreno.EventFlow.user.request.CreateUserRequestDto;
import br.com.matteusmoreno.EventFlow.user.request.UpdateUserRequestDto;
import br.com.matteusmoreno.EventFlow.user.response.UserDetailsResponseDto;
import br.com.matteusmoreno.EventFlow.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDetailsResponseDto> create(@RequestBody @Valid CreateUserRequestDto requestDto, UriComponentsBuilder uriBuilder) {
        User user = userService.createUser(requestDto);
        URI uri = uriBuilder.path("/personal-users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new UserDetailsResponseDto(user));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<UserDetailsResponseDto> detailsById(@PathVariable UUID id) {
        User user = userService.detailUserById(id);

        return ResponseEntity.ok(new UserDetailsResponseDto(user));
    }

    @PutMapping("/update")
    public ResponseEntity<UserDetailsResponseDto> update(@RequestBody @Valid UpdateUserRequestDto request) {
        User user = userService.updateUser(request);

        return ResponseEntity.ok(new UserDetailsResponseDto(user));
    }

    @DeleteMapping("/disable/{id}")
    public ResponseEntity<Void> disable(@PathVariable UUID id) {
        userService.disableUser(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<UserDetailsResponseDto> enable(@PathVariable UUID id) {
        User user = userService.enableUser(id);

        return ResponseEntity.ok(new UserDetailsResponseDto(user));
    }
}
