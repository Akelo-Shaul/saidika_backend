package com.shaul.saidikaV3.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

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
    private List<commentRating> comments;





}
