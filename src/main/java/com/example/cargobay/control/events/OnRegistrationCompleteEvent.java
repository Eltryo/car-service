package com.example.cargobay.control.events;

import com.example.cargobay.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.validation.constraints.NotBlank;

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    @NotBlank
    private final User user;

    public OnRegistrationCompleteEvent(User user) {
        super(user);
        this.user = user;
    }
}
