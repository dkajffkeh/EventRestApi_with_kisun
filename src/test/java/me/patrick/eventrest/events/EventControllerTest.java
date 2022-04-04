package me.patrick.eventrest.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createEvent() throws Exception {
        // given
        EventParam eventParam = EventParam.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018,11,23,14,21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018,11,23,14,21))
                .beginEventDateTime(LocalDateTime.of(2018,11,25,12,0))
                .endEventDateTime(LocalDateTime.of(2018,11,26,12,0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("Gangnam Station D8 Startup Factory")
                .build();

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .content(objectMapper.writeValueAsString(eventParam)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-event").exists())
        ;

    }

    @Test
    public void createEvent_BadRequest() throws Exception {
        // given
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018,11,23,14,21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018,11,23,14,21))
                .beginEventDateTime(LocalDateTime.of(2018,11,25,12,0))
                .endEventDateTime(LocalDateTime.of(2018,11,26,12,0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("Gangnam Station D8 Startup Factory")
                .free(true)
                .offline(false)
                .build();

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createEvent_BadRequest_Empty_Input() throws Exception {

        EventParam eventParam = EventParam.builder().build();

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .content(objectMapper.writeValueAsString(eventParam)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void createEvent_BadRequest_Wrong_Input() throws Exception {

        EventParam eventParam = EventParam.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018,11,24,14,21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018,11,23,14,21))
                .beginEventDateTime(LocalDateTime.of(2018,11,25,12,0))
                .endEventDateTime(LocalDateTime.of(2018,11,26,12,0))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("Gangnam Station D8 Startup Factory")
                .build();

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .content(objectMapper.writeValueAsString(eventParam)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
                ;
    }
}
