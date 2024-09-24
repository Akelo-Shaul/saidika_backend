package com.shaul.saidikaV3.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"os"})
public class service_provider extends Users{

    @OneToMany(mappedBy = "provider",fetch= FetchType.LAZY)
    @JsonProperty("os")
    private List<offered_services> offeredServices;


}
