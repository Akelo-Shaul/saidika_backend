package com.shaul.saidikaV3.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@JsonIgnoreProperties("chatters")
public class chatroom {
    @Id
   // @Column( columnDefinition="VARCHAR(128)")
    private String id;

   @OneToMany(mappedBy = "chat",fetch = FetchType.LAZY)
   @JsonIgnore
    List<messages> chat_messages;

    @ManyToMany
    @JsonProperty("chatters")
    @JoinTable(
            name = "person_chats",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<Users> chat_person;

   private String person1_name;
    private String person2_name;
    private String last_message_sent;
    private Timestamp lastMessage_timestamp;

    private UUID person1_id;
    private UUID person2_id;

}

