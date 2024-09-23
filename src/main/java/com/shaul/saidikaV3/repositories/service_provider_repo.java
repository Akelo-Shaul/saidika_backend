package com.shaul.saidikaV3.repositories;

import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.entities.service_provider;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface service_provider_repo extends users_repo<service_provider>{
    Optional<service_provider> findByEmail(String email);
}
