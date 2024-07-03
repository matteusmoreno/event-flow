package br.com.matteusmoreno.EventFlow.login.service;

import br.com.matteusmoreno.EventFlow.login.request.LoginRequestDto;
import br.com.matteusmoreno.EventFlow.role.Role;
import br.com.matteusmoreno.EventFlow.user.entity.User;
import br.com.matteusmoreno.EventFlow.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {

    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public LoginService(JwtEncoder jwtEncoder, BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public String login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.email());

        if (!userRepository.existsByEmail(request.email()) || isLoginCorrect(request, passwordEncoder, user)) {
            throw new BadCredentialsException("User or Password is invalid!");
        }

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("EventFlow")
                .subject(user.getEmail())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(600L))
                .claim("scope", setScopes(user))
                .claim("userId", user.getId())
                .claim("name", user.getName())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private boolean isLoginCorrect(LoginRequestDto request, PasswordEncoder passwordEncoder, User user) {
        return !passwordEncoder.matches(request.password(), user.getPassword());
    }

    private List<String> setScopes(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
