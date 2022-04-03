package me.patrick.eventrest.events;

import lombok.RequiredArgsConstructor;
import me.patrick.eventrest.repository.EventRepository;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        Event savedEvent = eventRepository.save(event);
        URI uri = linkTo(EventController.class).slash(savedEvent.getId()).toUri();
        return ResponseEntity.created(uri).body(event);
    }
}
