package com.shaul.saidikaV3.controllers;

import com.shaul.saidikaV3.auth.AuthService;
import com.shaul.saidikaV3.entities.*;
import com.shaul.saidikaV3.requestModels.messages_model;
import com.shaul.saidikaV3.services.chat_service;
import com.shaul.saidikaV3.services.messages_service;
import com.shaul.saidikaV3.services.service_finder_service;
import com.shaul.saidikaV3.services.service_provider_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/api/v1/messages")
public class message_controller {
    @Autowired
    AuthService pAuthservervice;
   @Autowired
   chat_service chatService;
   @Autowired
   messages_service messagesServices;
  @Autowired
  service_finder_service Sfs;
  @Autowired
  service_provider_service SPs;
   chatroom newChatRoom;

   @PreAuthorize("hasAuthority('FINDER') or hasAuthority('PROVIDER')")
   @PostMapping("/sendMessage/{recipient_id}")
 public chatroom send_message(@PathVariable UUID recipient_id, @RequestPart(value = "msg_model") messages_model msg_mod, @RequestPart(value = "image" ,required = false)MultipartFile hj) throws IOException {
   List<String> ids = new ArrayList<>();
   Users chat_person=pAuthservervice.getActiveProfile();
   ids.add(String.valueOf(chat_person.getId()));
   ids.add(String.valueOf(recipient_id));
   Collections.sort(ids);
   String chatId = ids.getFirst() + ids.getLast();
     //UUID CHatId= UUID.fromString(chatId);
String recipient_name=find_person(recipient_id).getFirst_name();


   if (chatService.find_chat_by_id(chatId).isEmpty()) {
       List<Users> ls=new ArrayList<>();
      newChatRoom = new chatroom();
      newChatRoom.setId(chatId);

           ls.add(chat_person);
           ls.add(find_person(recipient_id));

           newChatRoom.setChat_person(ls);




      chatService.create_chat(newChatRoom);

      //newChatRoom.setChat_messages();

//      newMessage.setText(msg_mod.getText());
//      newMessage.setChat(newChatRoom);
//      newMessage.setSender(sender_id);
//      newMessage.setRecipient(recipient_id);m
//      newMessage.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
     // return newChatRoom;
   } else if (chatService.find_chat_by_id(chatId).isPresent()) {
     newChatRoom = chatService.find_chat_by_id(chatId).orElse(null);


   }
   chatService.update_chat(newChatRoom,msg_mod.getText(),Timestamp.valueOf(LocalDateTime.now()),chat_person.getFirst_name(),recipient_name,chat_person.getId(),recipient_id);
   messages newMessage=new messages();
   String m_id=UUID.randomUUID().toString();
   newMessage.setId(m_id);
   newMessage.setText(msg_mod.getText());
   newMessage.setChat(newChatRoom);
   newMessage.setSender(chat_person.getId());
   newMessage.setRecipient(recipient_id);
   newMessage.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
    //newMessage.setChat_image_Url(store_photo_image(hj));
   messagesServices.add_message(newMessage);

   return newChatRoom;
   //return chatId;
}

@GetMapping("/get_messages/{chat_id}")
public ResponseEntity<?> getMessages(@PathVariable String chat_id) throws DataFormatException, IOException {
    if (chatService.find_chat_by_id(chat_id).isEmpty()){
        return ResponseEntity.ok("no such chat");
    }else
        return ResponseEntity.ok(chatService.get_chat_messages(chat_id));

}


    public Users find_person(UUID id_d){
       if(Sfs.find_by_id(id_d).isPresent()){
           return Sfs.find_by_id(id_d).orElse(null);

       }else
          return SPs.find_by_id(id_d).orElse(null);
    }
    @PreAuthorize("hasAuthority('FINDER') or hasAuthority('PROVIDER')")
    @GetMapping("/get_person_messages")
    public List<chatroom> get_person_Messages(){
        Users my_person=pAuthservervice.getActiveProfile();
        return my_person.getCHAts();
    }

    public String store_photo_image(MultipartFile fl) throws IOException {
       String filename=fl.getOriginalFilename().toString();
        String folder_path="C:\\Users\\Administrator\\Desktop\\saidika backend\\saidika_backend\\src\\main\\java\\com\\shaul\\saidikaV3\\chat_Images\\";
        String photo_path=folder_path+filename;
       fl.transferTo(new File(photo_path));
       String picture_Url="/api/v1/messages/getChatImage/"+filename;
  return picture_Url;
    }
    @GetMapping("/getChatImage/{picname}")
    public ResponseEntity<?> getPic(@PathVariable String picname) throws IOException {
        String folder_path="C:\\Users\\Administrator\\Desktop\\saidika backend\\saidika_backend\\src\\main\\java\\com\\shaul\\saidikaV3\\chat_Images\\";
        String pic_path=folder_path+picname;
        byte[] pp = Files.readAllBytes(new File(pic_path).toPath());
        return ResponseEntity.status(200)
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(pp);
    }
}
