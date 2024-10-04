package com.example.cargobay.control.service;

import com.example.cargobay.entity.User;
import com.example.cargobay.entity.VerificationToken;
import com.example.cargobay.repository.VerificationTokenRepository;
import com.example.cargobay.utility.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class VerificationService {
    private final VerificationTokenRepository verificationTokenRepository;

    public void createVerificationToken(User user, String uuid) {
        final Calendar cal = Calendar.getInstance();
        long time = new Date().getTime();
        cal.setTimeInMillis(time);
        cal.add(Calendar.MINUTE, 60 * 24);

        VerificationToken verificationToken = VerificationToken.builder()
                .token(uuid)
                .user(user)
                .expiryDate(cal.getTime())
                .build();


        verificationTokenRepository.save(verificationToken);
    }

    public VerificationToken validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new AppException("Invalid token", HttpStatus.BAD_REQUEST));

        verificationTokenRepository.delete(verificationToken);

        return verificationToken;
    }
}
