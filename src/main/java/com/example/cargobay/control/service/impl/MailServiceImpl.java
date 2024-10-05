package com.example.cargobay.control.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailServiceImpl{
    private final JavaMailSender mailSender;

    public void sendVerificationMail(String recipientAddress, String token){
        String content;
        try {
            content = loadFromFile("src/main/resources/templates/email/verificationEmail");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> substitutionMap = Map.of(
                "verification_code", token
        );
        String replace = new StringSubstitutor(substitutionMap).replace(content);

        SimpleMailMessage message = constructMailMessage(
                recipientAddress,
                "Verification of registration",
                replace
        );

        mailSender.send(message);
    }

    private SimpleMailMessage constructMailMessage(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        //todo: change email address
        message.setFrom("david.merkl.dm@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        return message;
    }

    private String loadFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}