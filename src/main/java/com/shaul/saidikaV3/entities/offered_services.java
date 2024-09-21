package com.shaul.saidikaV3.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@JsonIgnoreProperties({"rvs"})
public class offered_services {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID uuid;
    private String name;
    private String description;

    @ManyToOne
    private service_provider provider;

    @OneToMany(mappedBy = "offer_services",fetch = FetchType.LAZY)
    @JsonProperty("rvs")
    private List<commentRating> reviews;

}
