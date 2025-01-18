package com.shaul.saidikaV3.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.shaul.saidikaV3.entities.service_provider;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface service_provider_repo extends users_repo<service_provider>{
    Optional<service_provider> findByEmail(String email);
}
