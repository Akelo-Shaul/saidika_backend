package com.shaul.saidikaV3.repositories;


import com.shaul.saidikaV3.entities.messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface messages_repo extends JpaRepository<messages, String> {
}
