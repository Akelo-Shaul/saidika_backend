package com.shaul.saidikaV3.requestModels;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class offered_services_model {
   
    @NotEmpty
    private String name;
   
    @NotEmpty
    private String description;
     

}
