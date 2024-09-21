package com.shaul.saidikaV3.repositories;

import com.shaul.saidikaV3.entities.chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface chatroom_repo extends JpaRepository<chatroom, String> {
}
