package com.shaul.saidikaV3.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.DataFormatException;

import com.shaul.saidikaV3.entities.messages;
import com.shaul.saidikaV3.utils.imageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shaul.saidikaV3.entities.chatroom;
import com.shaul.saidikaV3.repositories.chatroom_repo;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;


@Service
public class chat_service {
    @Autowired
    chatroom_repo crr;
    @Autowired
    messages_service mSs;
    public chatroom create_chat(chatroom chr){
      return crr.saveAndFlush(chr);
    }
   public Optional<chatroom> find_chat_by_id(String id){
       return crr.findById(id);
   }

public ResponseEntity<?> get_chat_messages(String id) throws DataFormatException, IOException {
        chatroom cHAtroom=crr.findById(id).orElse(null);

//    HttpHeaders hd=new HttpHeaders();
//    hd.setContentType(MediaType.IMAGE_PNG);
//    hd.setContentType(MediaType.IMAGE_JPEG);
//    ContentDisposition build= ContentDisposition.attachment().build();
//    hd.setContentDisposition(build);
  return  ResponseEntity.status(200)
            .body(mSs.getMEssages(cHAtroom));

}
public String update_chat(chatroom CHAtroom, String last_msg, Timestamp gj, String s_name, String r_name, UUID id1, UUID id2){

    CHAtroom.setLast_message_sent(last_msg);
    CHAtroom.setLastMessage_timestamp(gj);
    CHAtroom.setPerson1_id(id1);
    CHAtroom.setPerson2_id(id2);

    if (CHAtroom.getPerson1_name() == null && CHAtroom.getPerson2_name() == null){
        CHAtroom.setPerson1_name(s_name);
        CHAtroom.setPerson2_name(r_name);
    }
    else
        return "names already exist";
    crr.saveAndFlush(CHAtroom);
  return "done";
}
}
