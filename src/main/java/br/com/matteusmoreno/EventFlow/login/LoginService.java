package br.com.matteusmoreno.EventFlow.login;

import br.com.matteusmoreno.EventFlow.personal_user.entity.PersonalUser;
import br.com.matteusmoreno.EventFlow.personal_user.repository.PersonalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LoginService {

    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PersonalUserRepository personalUserRepository;

    @Autowired
    public LoginService(JwtEncoder jwtEncoder, BCryptPasswordEncoder passwordEncoder, PersonalUserRepository personalUserRepository) {
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
        this.personalUserRepository = personalUserRepository;
    }

    public String login(LoginRequestDto request) {
        PersonalUser personalUser = personalUserRepository.findByEmail(request.email());

        if (!personalUserRepository.existsByEmail(request.email()) || isLoginCorrect(request, passwordEncoder, personalUser)) {
            throw new BadCredentialsException("user or password is invalid!");
        }

        var claims = JwtClaimsSet.builder()
                .issuer("EventFlow")
                .subject(personalUser.getEmail())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(300L))
                .claim("roles", "personal_user")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private boolean isLoginCorrect(LoginRequestDto request, PasswordEncoder passwordEncoder, PersonalUser personalUser) {
        return !passwordEncoder.matches(request.password(), personalUser.getPassword());
    }
}
