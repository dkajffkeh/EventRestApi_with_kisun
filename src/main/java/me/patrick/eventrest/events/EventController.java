package me.patrick.eventrest.events;

import lombok.RequiredArgsConstructor;
import me.patrick.eventrest.repository.EventRepository;
import me.patrick.eventrest.resources.EventResource;
import me.patrick.eventrest.validator.EventValidator;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventParam eventParam, Errors errors){

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }

        eventValidator.validate(eventParam,errors);
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }

        Event event = modelMapper.map(eventParam,Event.class);
        event = eventRepository.save(event);
        URI uri = linkTo(EventController.class).slash(event.getId()).toUri();
        ControllerLinkBuilder controllerLinkBuilder = linkTo(EventController.class).slash(event.getId());
        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(controllerLinkBuilder.withSelfRel());
        eventResource.add(controllerLinkBuilder.withRel("update-event"));
        return ResponseEntity.created(uri).body(eventResource);
    }
}
