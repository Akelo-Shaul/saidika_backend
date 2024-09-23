package com.shaul.saidikaV3.auth.AuthToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<AuthorizationToken,UUID> {

    Optional<AuthorizationToken> findByValue(String value);

    List<AuthorizationToken> findByProfileId(UUID uid);


    @Query("select max(lastAccess) from AuthorizationToken where profileId=?1")
    Long findLatestAccess(UUID profileId);
}
