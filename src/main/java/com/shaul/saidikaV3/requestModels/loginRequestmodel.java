package com.shaul.saidikaV3.requestModels;



import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class loginRequestmodel {


    @Email
   @Nonnull
    private String email;
    @Nonnull
    private String password;
}
