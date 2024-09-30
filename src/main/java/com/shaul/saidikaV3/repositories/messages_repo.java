package com.shaul.saidikaV3.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.shaul.saidikaV3.entities.messages;



public interface messages_repo extends JpaRepository<messages, String> {
}
