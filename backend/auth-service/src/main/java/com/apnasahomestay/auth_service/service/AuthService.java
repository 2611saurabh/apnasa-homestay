package com.apnasahomestay.auth_service.service;

import com.apnasahomestay.auth_service.domain.entity.AppUser;
import com.apnasahomestay.auth_service.dto.LoginRequest;
import com.apnasahomestay.auth_service.dto.LoginResponse;
import com.apnasahomestay.auth_service.exception.EmailAlreadyExistsException;
import com.apnasahomestay.auth_service.exception.UsernameAlreadyExistsException;
import com.apnasahomestay.auth_service.repository.UserRepository;
import com.apnasahomestay.auth_service.security.JwtService;
import com.apnasahomestay.auth_service.utility.Role;
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

        AppUser user = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return new LoginResponse(token);
    }

    public void register(String username, String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        if(userRepository.findByUsername(username).isPresent()){
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        AppUser user = AppUser.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .role(Role.USER)
                .status("ACTIVE")
                .emailVerified(false)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}