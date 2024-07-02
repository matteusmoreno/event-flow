package br.com.matteusmoreno.EventFlow.user.service;

import br.com.matteusmoreno.EventFlow.address.service.AddressService;
import br.com.matteusmoreno.EventFlow.role.RoleRepository;
import br.com.matteusmoreno.EventFlow.user.entity.User;
import br.com.matteusmoreno.EventFlow.user.repository.UserRepository;
import br.com.matteusmoreno.EventFlow.user.request.CreateUserRequestDto;
import br.com.matteusmoreno.EventFlow.user.request.UpdateUserRequestDto;
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
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressService addressService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, AddressService addressService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
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

        return user;
    }

    public User detailUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        verifyAuthenticatedUser(user);
        return user;
    }

    @Transactional
    public User updateUser(UpdateUserRequestDto request) {
        User user = userRepository.findById(request.id()).orElseThrow();
        verifyAuthenticatedUser(user);

        if (request.name() != null) {
            user.setName(request.name());
        }
        if (request.birthDate() != null) {
            user.setBirthDate(request.birthDate());
        }
        if (request.phone() != null) {
            user.setPhone(request.phone());
        }
        if (request.zipcode() != null) {
            user.setAddress(addressService.createAddress(request.zipcode()));
        }
        if (request.email() != null) {
            user.setEmail(request.email());
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }

    @Transactional
    public void disableUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        verifyAuthenticatedUser(user);
        user.setActive(false);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);

    }

    @Transactional
    public User enableUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        verifyAuthenticatedUser(user);
        user.setActive(true);
        user.setDeletedAt(null);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }


    private static void verifyAuthenticatedUser(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getName().equals(user.getEmail())) {
            throw new BadCredentialsException("You can't access this user");
        }
    }

}
