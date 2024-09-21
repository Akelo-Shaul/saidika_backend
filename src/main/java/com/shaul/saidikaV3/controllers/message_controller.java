package com.shaul.saidikaV3.controllers;

import com.shaul.saidikaV3.entities.chatroom;
import com.shaul.saidikaV3.entities.messages;
import com.shaul.saidikaV3.requestModels.messages_model;
import com.shaul.saidikaV3.services.chat_service;
import com.shaul.saidikaV3.services.messages_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
public class message_controller {

   @Autowired
   chat_service chatService;
   @Autowired
   messages_service messagesServices;

   chatroom newChatRoom;

@PostMapping("{sender_id}/{recipient_id}")
 public chatroom send_message(@PathVariable UUID sender_id, @PathVariable UUID recipient_id, @RequestBody messages_model msg_mod) {
   List<String> ids = new ArrayList<>();
   ids.add(String.valueOf(sender_id));
   ids.add(String.valueOf(recipient_id));
   Collections.sort(ids);
   String chatId = ids.getFirst() + ids.getLast();
     //UUID CHatId= UUID.fromString(chatId);



   if (chatService.find_chat_by_id(chatId).isEmpty()) {

      newChatRoom = new chatroom();
      newChatRoom.setId(chatId);
      chatService.create_chat(newChatRoom);

      //newChatRoom.setChat_messages();

//      newMessage.setText(msg_mod.getText());
//      newMessage.setChat(newChatRoom);
//      newMessage.setSender(sender_id);
//      newMessage.setRecipient(recipient_id);
//      newMessage.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
     // return newChatRoom;
   } else if (chatService.find_chat_by_id(chatId).isPresent()) {
     newChatRoom = chatService.find_chat_by_id(chatId).orElse(null);


   }
    messages newMessage=new messages();
   String m_id=UUID.randomUUID().toString();
   newMessage.setId(m_id);
   newMessage.setText(msg_mod.getText());
   newMessage.setChat(newChatRoom);
   newMessage.setSender(sender_id);
   newMessage.setRecipient(recipient_id);
   newMessage.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

   messagesServices.add_message(newMessage);
   return newChatRoom;
   //return chatId;
}


}
