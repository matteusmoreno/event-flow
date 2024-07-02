package br.com.matteusmoreno.EventFlow.event.controller;

import br.com.matteusmoreno.EventFlow.event.entity.Event;
import br.com.matteusmoreno.EventFlow.event.request.CreateEventRequestDto;
import br.com.matteusmoreno.EventFlow.event.respose.EventDetailsResponseDto;
import br.com.matteusmoreno.EventFlow.event.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<EventDetailsResponseDto> create(@RequestBody @Valid CreateEventRequestDto request, UriComponentsBuilder uriBuilder) {
        Event event = eventService.createEvent(request);
        URI uri = uriBuilder.path("/events/create/{id}").buildAndExpand(event.getId()).toUri();

        return ResponseEntity.created(uri).body(new EventDetailsResponseDto(event));
    }
}
