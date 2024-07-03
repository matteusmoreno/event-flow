package br.com.matteusmoreno.EventFlow.user.service;

import br.com.matteusmoreno.EventFlow.address.service.AddressService;
import br.com.matteusmoreno.EventFlow.role.RoleRepository;
import br.com.matteusmoreno.EventFlow.user.entity.User;
import br.com.matteusmoreno.EventFlow.user.repository.UserRepository;
import br.com.matteusmoreno.EventFlow.user.request.CreateUserRequestDto;
import br.com.matteusmoreno.EventFlow.user.request.UpdateUserRequestDto;
import br.com.matteusmoreno.EventFlow.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressService addressService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AppUtils appUtils;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, AddressService addressService, BCryptPasswordEncoder passwordEncoder, AppUtils appUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
        this.appUtils = appUtils;
    }

    @Transactional
    public User createUser(CreateUserRequestDto request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);

        user.setAddress(addressService.createAddress(request.zipcode()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepository.findByName("PERSONAL"));

        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        userRepository.save(user);

        log.info("User created with success: {}", user.getName());
        return user;
    }

    public User detailUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        appUtils.verifyAuthenticatedUser(user);

        log.info("User details requested: {}", user.getName());
        return user;
    }

    @Transactional
    public User updateUser(UpdateUserRequestDto request) {
        User user = userRepository.findById(request.id()).orElseThrow();
        appUtils.verifyAuthenticatedUser(user);

        if (request.name() != null) {
            user.setName(request.name());
            log.info("User {} updated name from {} to {}", user.getName(), user.getName(), request.name());
        }
        if (request.birthDate() != null) {
            user.setBirthDate(request.birthDate());
            log.info("User {} updated birthDate from {} to {}", user.getName(), user.getBirthDate(), request.birthDate());
        }
        if (request.phone() != null) {
            user.setPhone(request.phone());
            log.info("User {} updated phone from {} to {}", user.getName(), user.getPhone(), request.phone());
        }
        if (request.zipcode() != null) {
            user.setAddress(addressService.createAddress(request.zipcode()));
            log.info("User {} updated zipcode from {} to {}", user.getName(), user.getAddress().getZipcode(), request.zipcode());
        }
        if (request.email() != null) {
            user.setEmail(request.email());
            log.info("User {} updated email from {} to {}", user.getName(), user.getEmail(), request.email());
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }

    @Transactional
    public User addBusinessRole(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        appUtils.verifyAuthenticatedUser(user);

        if (user.getRoles().contains(roleRepository.findByName("BUSINESS"))) {
            throw new IllegalStateException("User already has business role");
        }

        user.getRoles().add(roleRepository.findByName("BUSINESS"));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        log.info("User {} added business role", user.getName());
        return user;
    }

    @Transactional
    public void disableUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        appUtils.verifyAuthenticatedUser(user);
        user.setActive(false);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);

        log.info("User {} disabled", user.getName());

    }

    @Transactional
    public User enableUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        appUtils.verifyAuthenticatedUser(user);
        user.setActive(true);
        user.setDeletedAt(null);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        log.info("User {} enabled", user.getName());
        return user;
    }



}
