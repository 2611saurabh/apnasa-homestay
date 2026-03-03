package com.apnasahomestay.auth_service.domain.entity;

import com.apnasahomestay.auth_service.domain.entity.property.Property;
import com.apnasahomestay.auth_service.utility.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String phone;

    private String status;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToMany(mappedBy = "coHosts")
    private List<Property> coHostedProperties;
}