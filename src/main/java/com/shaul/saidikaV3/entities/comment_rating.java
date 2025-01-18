package com.shaul.saidikaV3.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties({"commenter","os"})
public class comment_rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String comment;
    private Integer rating;

    @ManyToOne
    @JsonProperty("commenter")
    private Users commenter;


    @ManyToOne
    @JsonProperty("os")
    private offered_services offerservices;




}
