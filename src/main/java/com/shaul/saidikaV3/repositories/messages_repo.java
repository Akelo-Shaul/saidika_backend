package com.shaul.saidikaV3.repositories;


import com.shaul.saidikaV3.entities.chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shaul.saidikaV3.entities.messages;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface messages_repo extends JpaRepository<messages, String> {
    @Query("select m from messages m where chat=?1 order by timestamp asc")
  Optional< List<messages> >findByChat(chatroom chr);
}
