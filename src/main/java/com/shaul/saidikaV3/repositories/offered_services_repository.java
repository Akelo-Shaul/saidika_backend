package com.shaul.saidikaV3.repositories;

import com.shaul.saidikaV3.entities.offered_services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface offered_services_repository extends JpaRepository<offered_services, UUID> {
}
