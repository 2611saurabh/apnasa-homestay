package com.apnasahomestay.auth_service.repository;

import com.apnasahomestay.auth_service.domain.entity.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property,Long> {
}
