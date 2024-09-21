package com.shaul.saidikaV3.services;

import com.shaul.saidikaV3.entities.chatroom;
import com.shaul.saidikaV3.repositories.chatroom_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class chat_service {
    @Autowired
    chatroom_repo crr;
    public chatroom create_chat(chatroom chr){
      return crr.save(chr);
    }
   public Optional<chatroom> find_chat_by_id(String id){
       return crr.findById(id);
   }

}
