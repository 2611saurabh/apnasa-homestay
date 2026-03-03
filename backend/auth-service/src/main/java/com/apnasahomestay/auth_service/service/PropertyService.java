package com.apnasahomestay.auth_service.service;

import com.apnasahomestay.auth_service.domain.entity.AppUser;
import com.apnasahomestay.auth_service.domain.entity.property.Property;
import com.apnasahomestay.auth_service.repository.PropertyRepository;
import com.apnasahomestay.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public Property createProperty(String title, String username) {
        AppUser host = userRepository.findByUsername(username).orElseThrow();

        Property property = Property.builder()
                .title(title)
                .host(host)
                .build();

        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, String title, String username) {
        Property property = propertyRepository.findById(id).orElseThrow();
        AppUser user = userRepository.findByUsername(username).orElseThrow();

        if (!property.getHost().getId().equals(user.getId())
                && !property.getCoHosts().contains(user)) {
            throw new AccessDeniedException("Not allowed");
        }

        property.setTitle(title);
        return propertyRepository.save(property);
    }

    public void addCoHost(Long propertyId, Long userId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow();
        AppUser user = userRepository.findById(userId).orElseThrow();

        property.getCoHosts().add(user);
        propertyRepository.save(property);
    }
}
