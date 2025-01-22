package com.shaul.saidikaV3.responsemodels;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class login_response {

    private String message;
    private String Authorization;
    private UUID profile_id;
    private boolean first_time_login;
    private String profile_name;
}
