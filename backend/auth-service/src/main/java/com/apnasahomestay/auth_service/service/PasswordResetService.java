package com.apnasahomestay.auth_service.service;

import com.apnasahomestay.auth_service.domain.entity.AppUser;
import com.apnasahomestay.auth_service.domain.entity.PasswordResetToken;
import com.apnasahomestay.auth_service.exception.InvalidResetTokenException;
import com.apnasahomestay.auth_service.exception.TokenAlreadyUsedException;
import com.apnasahomestay.auth_service.exception.TokenExpiredException;
import com.apnasahomestay.auth_service.repository.PasswordResetTokenRepository;
import com.apnasahomestay.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public void forgotPassword(String email) {

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        tokenRepository.deleteByUser(user);

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(30))
                .createdAt(LocalDateTime.now())
                .used(false)
                .build();

        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:3000/reset-password?token=" + token;

        emailService.sendResetEmail(user.getEmail(), resetLink);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidResetTokenException("Invalid reset token"));

        if (resetToken.isUsed()) {
            throw new TokenAlreadyUsedException("Token already used");
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token expired");
        }

        validatePassword(newPassword);

        AppUser user = resetToken.getUser();

        user.setPasswordHash(passwordEncoder.encode(newPassword));

        resetToken.setUsed(true);

        userRepository.save(user);
        tokenRepository.save(resetToken);
    }

    private void validatePassword(String password) {

        String pattern =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (!password.matches(pattern)) {
            throw new RuntimeException(
                    "Password must contain 8 characters, uppercase, lowercase, number, and special character"
            );
        }
    }
}