package com.shaul.saidikaV3.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@JsonIgnoreProperties({"commenter","os"})
public class commentRating {
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
    private offered_services offer_services;




}
