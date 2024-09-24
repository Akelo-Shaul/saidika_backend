package com.shaul.saidikaV3.requestModels;


import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class registerRequestModel {
     @NotEmpty
    private String first_name;

     @NotEmpty
    private String last_name;

    @Email
     @NotEmpty
    private String email;

     @NotEmpty
    private String phone;


   
    
     @NotEmpty
    private String role;



     @NotEmpty
    @Size(min = 3,message = "password must be greater than 3 characters",max=10)
    private String password;

     @NotEmpty
    private String confirmPassword;
}
