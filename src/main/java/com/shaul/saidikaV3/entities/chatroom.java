package com.shaul.saidikaV3.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Data
@Entity
public class chatroom {
    @Id
   // @Column( columnDefinition="VARCHAR(128)")
    private String id;

   @OneToMany(mappedBy = "chat",fetch = FetchType.LAZY)
    List<messages> chat_messages;
}

