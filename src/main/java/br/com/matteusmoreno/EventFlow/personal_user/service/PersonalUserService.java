package br.com.matteusmoreno.EventFlow.personal_user.service;

import br.com.matteusmoreno.EventFlow.address.service.AddressService;
import br.com.matteusmoreno.EventFlow.personal_user.entity.PersonalUser;
import br.com.matteusmoreno.EventFlow.personal_user.repository.PersonalUserRepository;
import br.com.matteusmoreno.EventFlow.personal_user.request.CreatePersonalUserRequestDto;
import br.com.matteusmoreno.EventFlow.personal_user.request.UpdatePersonalUserRequestDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PersonalUserService {

    private final PersonalUserRepository personalUserRepository;
    private final AddressService addressService;

    @Autowired
    public PersonalUserService(PersonalUserRepository personalUserRepository, AddressService addressService) {
        this.personalUserRepository = personalUserRepository;
        this.addressService = addressService;
    }

    @Transactional
    public PersonalUser createPersonalUser(CreatePersonalUserRequestDto request) {
        PersonalUser personalUser = new PersonalUser();
        BeanUtils.copyProperties(request, personalUser);

        personalUser.setAddress(addressService.createAddress(request.zipcode()));

        personalUser.setCreatedAt(LocalDateTime.now());
        personalUser.setActive(true);
        personalUserRepository.save(personalUser);

        return personalUser;
    }


    public PersonalUser detailPersonalUserById(UUID id) {
        return personalUserRepository.findById(id).orElseThrow();
    }

    @Transactional
    public PersonalUser updatePersonalUser(UpdatePersonalUserRequestDto request) {
        PersonalUser personalUser = personalUserRepository.findById(request.id()).orElseThrow();
        
        if (request.name() != null) {
            personalUser.setName(request.name());
        }
        if (request.birthDate() != null) {
            personalUser.setBirthDate(request.birthDate());
        }
        if (request.phone() != null) {
            personalUser.setPhone(request.phone());
        }
        if (request.zipcode() != null) {
            personalUser.setAddress(addressService.createAddress(request.zipcode()));
        }
        if (request.email() != null) {
            personalUser.setEmail(request.email());
        }

        personalUser.setUpdatedAt(LocalDateTime.now());
        personalUserRepository.save(personalUser);

        return personalUser;
    }

    @Transactional
    public void disablePersonalUser(UUID id) {
        PersonalUser personalUser = personalUserRepository.findById(id).orElseThrow();
        personalUser.setActive(false);
        personalUser.setDeletedAt(LocalDateTime.now());
        personalUserRepository.save(personalUser);

    }

    @Transactional
    public PersonalUser enablePersonalUser(UUID id) {
        PersonalUser personalUser = personalUserRepository.findById(id).orElseThrow();
        personalUser.setActive(true);
        personalUser.setDeletedAt(null);
        personalUser.setUpdatedAt(LocalDateTime.now());
        personalUserRepository.save(personalUser);

        return personalUser;
    }
}
