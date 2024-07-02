package br.com.matteusmoreno.EventFlow.event.repository;

import br.com.matteusmoreno.EventFlow.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
