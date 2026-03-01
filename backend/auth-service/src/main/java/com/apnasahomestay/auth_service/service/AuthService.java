package com.apnasahomestay.auth_service.service;

import com.apnasahomestay.auth_service.domain.entity.AppUser;
import com.apnasahomestay.auth_service.dto.LoginRequest;
import com.apnasahomestay.auth_service.dto.LoginResponse;
import com.apnasahomestay.auth_service.exception.EmailAlreadyExistsException;
import com.apnasahomestay.auth_service.repository.UserRepository;
import com.apnasahomestay.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String token = jwtService.generateToken(loginRequest.getUsername());

        return new LoginResponse(token);
    }

    public void register(String username, String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        AppUser user = AppUser.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .status("ACTIVE")
                .emailVerified(false)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}