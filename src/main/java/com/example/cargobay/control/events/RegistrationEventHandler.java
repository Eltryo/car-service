package com.example.cargobay.control.events;

import com.example.cargobay.control.service.VerificationService;
import com.example.cargobay.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationEventHandler implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final VerificationService verificationService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String uuid = UUID.randomUUID().toString();

        verificationService.createVerificationToken(user, uuid);
    }
}
