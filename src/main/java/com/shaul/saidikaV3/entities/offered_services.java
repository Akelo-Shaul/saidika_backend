package com.shaul.saidikaV3.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@JsonIgnoreProperties({"rvs"})
public class offered_services {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID uuid;
    private String name;
    private String description;
    private String availableLocation;
    private Integer average_rating;
    @ManyToOne
    private service_provider provider;

    @OneToMany(mappedBy = "offerservices",fetch = FetchType.LAZY)
    @JsonProperty("rvs")
    private List<comment_rating> reviews;

}
