package me.patrick.eventrest.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import me.patrick.eventrest.events.Event;
import me.patrick.eventrest.events.EventController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
public class EventResource extends Resource<Event> {

    public EventResource(Event event, Link... links) {
        super(event, links);
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }
}
