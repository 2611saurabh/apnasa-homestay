package com.apnasahomestay.auth_service.controller;

import com.apnasahomestay.auth_service.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PreAuthorize("hasRole('HOST')")
    @PostMapping
    public String create(@RequestParam String title, Authentication auth) {
        propertyService.createProperty(title, auth.getName());
        return "Created";
    }

    @PreAuthorize("hasAnyRole('HOST','CO_HOST')")
    @PutMapping("/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String title,
                         Authentication auth) {
        propertyService.updateProperty(id, title, auth.getName());
        return "Updated";
    }

    @PreAuthorize("hasRole('HOST')")
    @PostMapping("/{id}/cohost/{userId}")
    public String addCoHost(@PathVariable Long id,
                            @PathVariable Long userId) {
        propertyService.addCoHost(id, userId);
        return "CoHost Added";
    }
}
