package com.shaul.saidikaV3.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties({"comments"})
public class Users {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String role;
    private String password;

    @OneToMany(mappedBy = "commenter", fetch = FetchType.LAZY)
    @JsonProperty("comments")
    private List<comment_rating> comments;





}
