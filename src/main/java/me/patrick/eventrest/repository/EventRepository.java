package me.patrick.eventrest.repository;

import me.patrick.eventrest.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
