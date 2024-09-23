package com.shaul.saidikaV3.auth.TWOFactor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TwoFactorRepository extends JpaRepository<TwoFactor, UUID> {

    Optional<TwoFactor> findBySecret(String secret);

    Optional<TwoFactor> findByUserUid(UUID uid);

}
