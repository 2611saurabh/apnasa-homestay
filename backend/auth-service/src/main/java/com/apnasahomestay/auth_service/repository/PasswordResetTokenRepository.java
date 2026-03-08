package com.apnasahomestay.auth_service.repository;


import com.apnasahomestay.auth_service.domain.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


import com.apnasahomestay.auth_service.domain.entity.AppUser;
import com.apnasahomestay.auth_service.domain.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUser(AppUser user);
}
