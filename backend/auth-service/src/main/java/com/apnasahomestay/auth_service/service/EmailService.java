package com.apnasahomestay.auth_service.service;



import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendResetEmail(String toEmail, String resetLink) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Password Reset Request");
        message.setText(
                "You requested to reset your password.\n\n" +
                        "Click the link below to reset your password:\n" +
                        resetLink +
                        "\n\nIf you did not request this, please ignore this email."
        );

        mailSender.send(message);
    }
}
