package com.shaul.saidikaV3.auth.AuthToken;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shaul.saidikaV3.auth.Authorities.AccountRoles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class AuthorizationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;

    @Column(unique = true)
    private String value;

    private Long lastAccess;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean loggedOut;

    private Long inActiveTime= TimeUnit.DAYS.toMillis(30);

    private AccountRoles accountRole;

    public Boolean isUsable()
    {
        Long expiryTime=lastAccess+inActiveTime;
        return (!loggedOut&&new Date().before(new Date(expiryTime)));
    }

    private UUID profileId;

    @JsonIgnore
    private String verification;


    @JsonIgnore
    private Boolean authenticated;


}
