package com.shaul.saidikaV3.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@JsonIgnoreProperties({"cHat"})
public class messages {

    @Id
//    @SequenceGenerator(sequenceName = "message_sequence", name = "message_sequence", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.AUTO ,generator = "message_sequence")
    private String id;
    private String text;
    private UUID sender;
    private UUID recipient;
    private Timestamp timestamp;
    private String chat_image_Url;
    @ManyToOne
    @JsonProperty("cHat")
    private chatroom chat;

}
