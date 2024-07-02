package br.com.matteusmoreno.EventFlow.login;

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
            throw new BadCredentialsException("user or password is invalid!");
        }

        var claims = JwtClaimsSet.builder()
                .issuer("EventFlow")
                .subject(user.getEmail())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(300L))
                .claim("roles", "personal_user")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private boolean isLoginCorrect(LoginRequestDto request, PasswordEncoder passwordEncoder, User user) {
        return !passwordEncoder.matches(request.password(), user.getPassword());
    }
}
