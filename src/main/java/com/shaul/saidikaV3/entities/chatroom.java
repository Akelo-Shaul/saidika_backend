package com.shaul.saidikaV3.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class chatroom {
    @Id
   // @Column( columnDefinition="VARCHAR(128)")
    private String id;

   @OneToMany(mappedBy = "chat",fetch = FetchType.LAZY)
    List<messages> chat_messages;
}

