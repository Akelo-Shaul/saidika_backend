package com.shaul.saidikaV3.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@JsonIgnoreProperties({"comments","conversations"})
public class Users {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String role;

    @JsonIgnore
    private String password;
    @JsonIgnore 
    private String profile_Photo_Path;
    private String notif_token;

    @OneToMany(mappedBy = "commenter", fetch = FetchType.LAZY)
    @JsonProperty("comments")
    @JsonIgnore
    private List<comment_rating> comments;

    @ManyToMany(mappedBy = "chat_person")
    // @JsonProperty("conversations")
    @JsonIgnore
    private List<chatroom> CHAts;



}
