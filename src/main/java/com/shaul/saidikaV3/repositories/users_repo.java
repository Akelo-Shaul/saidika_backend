package com.shaul.saidikaV3.repositories;

import com.shaul.saidikaV3.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface users_repo<T extends Users> extends JpaRepository<T, UUID> {
}
