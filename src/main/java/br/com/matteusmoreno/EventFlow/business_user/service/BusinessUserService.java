package br.com.matteusmoreno.EventFlow.business_user.service;

import br.com.matteusmoreno.EventFlow.address.service.AddressService;
import br.com.matteusmoreno.EventFlow.business_user.entity.BusinessUser;
import br.com.matteusmoreno.EventFlow.business_user.repository.BusinessUserRepository;
import br.com.matteusmoreno.EventFlow.business_user.request.CreateBusinessUserRequestDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BusinessUserService {

    private final BusinessUserRepository businessUserRepository;
    private final AddressService addressService;

    @Autowired
    public BusinessUserService(BusinessUserRepository businessUserRepository, AddressService addressService) {
        this.businessUserRepository = businessUserRepository;
        this.addressService = addressService;
    }

    @Transactional
    public BusinessUser createBusinessUser(CreateBusinessUserRequestDto request) {
        BusinessUser businessUser = new BusinessUser();
        BeanUtils.copyProperties(request, businessUser);

        businessUser.setAddress(addressService.createAddress(request.zipcode()));
        businessUser.setCreatedAt(LocalDateTime.now());
        businessUser.setActive(true);

        businessUserRepository.save(businessUser);

        return businessUser;

    }
}
