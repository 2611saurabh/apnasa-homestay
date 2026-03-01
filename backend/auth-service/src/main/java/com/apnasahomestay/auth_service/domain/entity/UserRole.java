package com.apnasahomestay.auth_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "user_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserRoleId.class)
public class UserRole {

    @Id
    private Long userId;

    @Id
    private Long roleId;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;
}