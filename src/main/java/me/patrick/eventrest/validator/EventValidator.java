package me.patrick.eventrest.validator;

import me.patrick.eventrest.events.EventParam;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventParam eventParam, Errors errors){
        if(eventParam.getBasePrice() > eventParam.getMaxPrice() && eventParam.getMaxPrice() > 0){
            errors.rejectValue("basePrice","wrongValue","basePriceWrong");
            errors.rejectValue("maxPrice","wrongValue","maxPriceWrong");
        }

        LocalDateTime endEventDateTime = eventParam.getEndEventDateTime();
        if(endEventDateTime.isBefore(eventParam.getEndEventDateTime())
        || endEventDateTime.isBefore(eventParam.getCloseEnrollmentDateTime())
        || endEventDateTime.isBefore(eventParam.getBeginEnrollmentDateTime())
        ){
            errors.rejectValue("endEventDataTime","wrongValue","endEventDataTime is wrong");
        }


    }
}
