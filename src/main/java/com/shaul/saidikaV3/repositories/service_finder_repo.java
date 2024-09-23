package com.shaul.saidikaV3.repositories;

import com.shaul.saidikaV3.entities.service_finder;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface service_finder_repo extends users_repo<service_finder>{
Optional<service_finder> findByEmail(String email);
}
