package com.shaul.saidikaV3.auth.AuthToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthorizationToken,UUID> {

    Optional<AuthorizationToken> findByValue(String value);

    List<AuthorizationToken> findByProfileid(UUID uid);


    @Query("select max(lastAccess) from AuthorizationToken where profileid=?1")
    Long findLatestAccess(UUID profileid);
}
