package br.com.matteusmoreno.EventFlow.event.service;

import br.com.matteusmoreno.EventFlow.address.service.AddressService;
import br.com.matteusmoreno.EventFlow.event.entity.Event;
import br.com.matteusmoreno.EventFlow.event.repository.EventRepository;
import br.com.matteusmoreno.EventFlow.event.request.CreateEventRequestDto;
import br.com.matteusmoreno.EventFlow.event.request.UpdateEventRequestDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final AddressService addressService;

    @Autowired
    public EventService(EventRepository eventRepository, AddressService addressService) {
        this.eventRepository = eventRepository;
        this.addressService = addressService;
    }

    @Transactional
    public Event createEvent(CreateEventRequestDto request) {
        Event event = new Event();
        BeanUtils.copyProperties(request, event);

        event.setAddress(addressService.createAddress(request.zipcode()));
        event.setCreatedAt(LocalDateTime.now());
        event.setActive(true);

        eventRepository.save(event);

        return event;
    }

    public Event detailEventById(UUID id) {
        return eventRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Event updateEvent(UpdateEventRequestDto request) {
        Event event = eventRepository.findById(request.id()).orElseThrow();

        if (request.name() != null) {
            event.setName(request.name());
        }
        if (request.description() != null) {
            event.setDescription(request.description());
        }
        if (request.zipcode() != null) {
            event.setAddress(addressService.createAddress(request.zipcode()));
        }
        if (request.date() != null) {
            event.setDate(request.date());
        }
        if (request.price() != null) {
            event.setPrice(request.price());
        }
        if (request.ticketAvailable() != null) {
            event.setTicketAvailable(request.ticketAvailable());
        }

        event.setUpdatedAt(LocalDateTime.now());
        eventRepository.save(event);

        return event;
    }
}
