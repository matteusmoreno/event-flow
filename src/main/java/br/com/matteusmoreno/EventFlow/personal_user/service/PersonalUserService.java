package br.com.matteusmoreno.EventFlow.personal_user.service;

import br.com.matteusmoreno.EventFlow.address.repository.AddressRepository;
import br.com.matteusmoreno.EventFlow.address.service.AddressService;
import br.com.matteusmoreno.EventFlow.personal_user.entity.PersonalUser;
import br.com.matteusmoreno.EventFlow.personal_user.repository.PersonalUserRepository;
import br.com.matteusmoreno.EventFlow.personal_user.request.CreatePersonalUserRequestDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        if (addressRepository.existsByZipcode(request.zipcode())) {
            personalUser.setAddress(addressRepository.findByZipcode(request.zipcode()));
        } else {
            personalUser.setAddress(addressService.createAddress(request.zipcode()));
        }

        personalUser.setCreatedAt(LocalDateTime.now());
        personalUser.setActive(true);
        personalUserRepository.save(personalUser);

        return personalUser;
    }
}
