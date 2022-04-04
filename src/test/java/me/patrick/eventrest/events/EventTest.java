package me.patrick.eventrest.events;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    public void builder(){
        EventParam event = EventParam.builder().build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean(){

        String name ="Event";
        String description = "Spring";

        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);

    }

}