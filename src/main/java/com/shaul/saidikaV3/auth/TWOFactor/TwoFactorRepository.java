package com.shaul.saidikaV3.auth.TWOFactor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorRepository extends JpaRepository<TwoFactor, UUID> {

    Optional<TwoFactor> findBySecret(String secret);

    Optional<TwoFactor> findByUser_id(UUID uid);

}
