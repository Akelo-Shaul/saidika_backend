package com.shaul.saidikaV3.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class otp {

  private String email;

  @Id
  private String otpToken;


}
