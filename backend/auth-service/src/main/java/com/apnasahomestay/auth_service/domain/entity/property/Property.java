package com.apnasahomestay.auth_service.domain.entity.property;

import com.apnasahomestay.auth_service.domain.entity.AppUser;
import jakarta.persistence.*;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private AppUser host;

    @ManyToMany
    @JoinTable(
            name = "property_cohosts",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> coHosts;
}