package com.shaul.saidikaV3.auth.TWOFactor;


import com.shaul.saidikaV3.auth.Authorities.AccountRoles;
import com.shaul.saidikaV3.entities.Users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TwoFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private AccountRoles accountRole;

    private String secret;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isVerified;

    private Long createdOn;
}
