package br.com.matteusmoreno.EventFlow.credit_card;

import br.com.matteusmoreno.EventFlow.user.entity.User;
import br.com.matteusmoreno.EventFlow.user.repository.UserRepository;
import br.com.matteusmoreno.EventFlow.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final AppUtils appUtils;
    private final UserRepository userRepository;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository, AppUtils appUtils, UserRepository userRepository) {
        this.creditCardRepository = creditCardRepository;
        this.appUtils = appUtils;
        this.userRepository = userRepository;
    }

    @Transactional
    public CreditCard createCreditCard(CreateCreditCardRequestDto request) {
        CreditCard creditCard = new CreditCard();
        User user = appUtils.getAuthenticatedUser();

        BeanUtils.copyProperties(request, creditCard);

        creditCard.setName(request.name().toUpperCase());
        creditCard.setUser(userRepository.findByEmail(user.getEmail()));
        creditCard.setCreatedAt(LocalDateTime.now());
        creditCard.setActive(true);

        creditCardRepository.save(creditCard);

        log.info("Credit card created by: {}", user.getName());
        return creditCard;
    }

    public CreditCard detailCreditCardById(Long id) {
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow();
        User user = creditCard.getUser();

        appUtils.verifyAuthenticatedUser(user);
        log.info("Credit card details by: {}", appUtils.getAuthenticatedUser().getName());
        return creditCard;
    }

    @Transactional
    public void disableCreditCard(Long id) {
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow();
        User user = creditCard.getUser();

        appUtils.verifyAuthenticatedUser(user);
        creditCard.setDeletedAt(LocalDateTime.now());
        creditCard.setActive(false);
        creditCardRepository.save(creditCard);

        log.info("Credit card disabled by: {}", appUtils.getAuthenticatedUser().getName());

    }

    @Transactional
    public CreditCard enableCreditCard(Long id) {
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow();
        User user = creditCard.getUser();

        appUtils.verifyAuthenticatedUser(user);
        creditCard.setDeletedAt(null);
        creditCard.setUpdatedAt(LocalDateTime.now());
        creditCard.setActive(true);
        creditCardRepository.save(creditCard);

        log.info("Credit card enabled by: {}", appUtils.getAuthenticatedUser().getName());
        return creditCard;
    }
}
