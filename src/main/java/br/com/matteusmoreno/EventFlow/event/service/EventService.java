package br.com.matteusmoreno.EventFlow.event.service;

import br.com.matteusmoreno.EventFlow.address.service.AddressService;
import br.com.matteusmoreno.EventFlow.event.entity.Event;
import br.com.matteusmoreno.EventFlow.event.repository.EventRepository;
import br.com.matteusmoreno.EventFlow.event.request.CreateEventRequestDto;
import br.com.matteusmoreno.EventFlow.event.request.UpdateEventRequestDto;
import br.com.matteusmoreno.EventFlow.user.entity.User;
import br.com.matteusmoreno.EventFlow.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class EventService{

    private final EventRepository eventRepository;
    private final AddressService addressService;
    private final UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, AddressService addressService, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.addressService = addressService;
        this.userRepository = userRepository;
    }

    @Transactional
    public Event createEvent(CreateEventRequestDto request) {
        Event event = new Event();
        User user = getAuthenticatedUser();

        BeanUtils.copyProperties(request, event);

        event.setAddress(addressService.createAddress(request.zipcode()));
        event.setCreatedAt(LocalDateTime.now());
        event.setUser(user);
        event.setActive(true);

        eventRepository.save(event);

        log.info("User {} created event {}", user.getName(), event.getName());
        return event;
    }

    public Event detailEventById(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow();

        log.info("User {} detail event {}", getAuthenticatedUser().getName(), event.getName());
        return event;
    }

    @Transactional
    public Event updateEvent(UpdateEventRequestDto request) {
        Event event = eventRepository.findById(request.id()).orElseThrow();
        verifyAuthenticatedUser(event);

        if (request.name() != null) {
            event.setName(request.name());
            log.info("User {} updated name from {} to {}", event.getUser().getName(), event.getName(), request.name());

        }
        if (request.description() != null) {
            event.setDescription(request.description());
            log.info("User {} updated description from {} to {}", event.getUser().getName(), event.getDescription(), request.description());
        }
        if (request.zipcode() != null) {
            event.setAddress(addressService.createAddress(request.zipcode()));
            log.info("User {} updated zipcode from {} to {}", event.getUser().getName(), event.getAddress().getZipcode(), request.zipcode());
        }
        if (request.date() != null) {
            event.setDate(request.date());
            log.info("User {} updated date from {} to {}", event.getUser().getName(), event.getDate(), request.date());
        }
        if (request.price() != null) {
            event.setPrice(request.price());
            log.info("User {} updated price from {} to {}", event.getUser().getName(), event.getPrice(), request.price());
        }
        if (request.ticketAvailable() != null) {
            event.setTicketAvailable(request.ticketAvailable());
            log.info("User {} updated ticketAvailable from {} to {}", event.getUser().getName(), event.getTicketAvailable(), request.ticketAvailable());
        }

        event.setUpdatedAt(LocalDateTime.now());
        eventRepository.save(event);

        return event;
    }

    @Transactional
    public void disableEvent(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow();
        verifyAuthenticatedUser(event);
        event.setDeletedAt(LocalDateTime.now());
        event.setActive(false);
        eventRepository.save(event);

        log.info("User {} disabled event {}", event.getUser().getName(), event.getName());
    }

    @Transactional
    public Event enableEvent(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow();
        verifyAuthenticatedUser(event);
        event.setDeletedAt(null);
        event.setActive(true);
        event.setUpdatedAt(LocalDateTime.now());
        eventRepository.save(event);

        log.info("User {} enabled event {}", event.getUser().getName(), event.getName());
        return event;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName());
    }


    private void verifyAuthenticatedUser(Event event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.equals(event.getUser().getEmail(), authentication.getName())) {
            log.error("User {} tried to access event {}", authentication.getName(), event.getName());
            throw new BadCredentialsException("You can't access this event");
        }
    }
}
