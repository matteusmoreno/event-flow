package br.com.matteusmoreno.EventFlow.personal_user.service;

import br.com.matteusmoreno.EventFlow.address.repository.AddressRepository;
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
    private final AddressRepository addressRepository;

    @Autowired
    public PersonalUserService(PersonalUserRepository personalUserRepository, AddressService addressService, AddressRepository addressRepository) {
        this.personalUserRepository = personalUserRepository;
        this.addressService = addressService;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public PersonalUser createPersonalUser(CreatePersonalUserRequestDto request) {
        PersonalUser personalUser = new PersonalUser();
        BeanUtils.copyProperties(request, personalUser);

        setAddressAttributes(request.zipcode(), personalUser);

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
            setAddressAttributes(request.zipcode(), personalUser);
        }
        if (request.email() != null) {
            personalUser.setEmail(request.email());
        }

        personalUser.setUpdatedAt(LocalDateTime.now());
        personalUserRepository.save(personalUser);

        return personalUser;
    }

    @Transactional
    public PersonalUser disablePersonalUser(UUID id) {
        PersonalUser personalUser = personalUserRepository.findById(id).orElseThrow();
        personalUser.setActive(false);
        personalUser.setDeletedAt(LocalDateTime.now());
        personalUserRepository.save(personalUser);

        return personalUser;
    }

    private void setAddressAttributes(String zipcode, PersonalUser personalUser) {
        if (addressRepository.existsByZipcode(zipcode)) {
            personalUser.setAddress(addressRepository.findByZipcode(zipcode));
        } else {
            personalUser.setAddress(addressService.createAddress(zipcode));
        }
    }
}
