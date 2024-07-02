package br.com.matteusmoreno.EventFlow.business_user.service;

import br.com.matteusmoreno.EventFlow.address.service.AddressService;
import br.com.matteusmoreno.EventFlow.business_user.entity.BusinessUser;
import br.com.matteusmoreno.EventFlow.business_user.repository.BusinessUserRepository;
import br.com.matteusmoreno.EventFlow.business_user.request.CreateBusinessUserRequestDto;
import br.com.matteusmoreno.EventFlow.personal_user.request.UpdateBusinessUserRequestDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BusinessUserService {

    private final BusinessUserRepository businessUserRepository;
    private final AddressService addressService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public BusinessUserService(BusinessUserRepository businessUserRepository, AddressService addressService, BCryptPasswordEncoder passwordEncoder) {
        this.businessUserRepository = businessUserRepository;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public BusinessUser createBusinessUser(CreateBusinessUserRequestDto request) {
        BusinessUser businessUser = new BusinessUser();
        BeanUtils.copyProperties(request, businessUser);

        businessUser.setPassword(passwordEncoder.encode(businessUser.getPassword()));
        businessUser.setAddress(addressService.createAddress(request.zipcode()));
        businessUser.setCreatedAt(LocalDateTime.now());
        businessUser.setActive(true);

        businessUserRepository.save(businessUser);

        return businessUser;

    }

    public BusinessUser detailBusinessUserById(UUID id) {
        return businessUserRepository.findById(id).orElseThrow();
    }

    @Transactional
    public BusinessUser updateBusinessUser(UpdateBusinessUserRequestDto request) {
        BusinessUser businessUser = businessUserRepository.findById(request.id()).orElseThrow();

        if (request.name() != null) {
            businessUser.setName(request.name());
        }
        if (request.phone() != null) {
            businessUser.setPhone(request.phone());
        }
        if (request.zipcode() != null) {
            businessUser.setAddress(addressService.createAddress(request.zipcode()));
        }
        if (request.email() != null) {
            businessUser.setEmail(request.email());
        }
        if (request.password() != null) {
            businessUser.setPassword(request.password());
        }

        businessUser.setUpdatedAt(LocalDateTime.now());
        businessUserRepository.save(businessUser);

        return businessUser;
    }

    @Transactional
    public void disableBusinessUser(UUID id) {
        BusinessUser businessUser = businessUserRepository.findById(id).orElseThrow();
        businessUser.setActive(false);
        businessUser.setDeletedAt(LocalDateTime.now());
        businessUserRepository.save(businessUser);
    }

    @Transactional
    public BusinessUser enableBusinessUser(UUID id) {
        BusinessUser businessUser = businessUserRepository.findById(id).orElseThrow();
        businessUser.setActive(true);
        businessUser.setDeletedAt(null);
        businessUser.setUpdatedAt(LocalDateTime.now());
        businessUserRepository.save(businessUser);

        return businessUser;
    }
}
