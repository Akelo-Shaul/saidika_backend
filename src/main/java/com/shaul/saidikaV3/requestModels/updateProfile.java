package com.shaul.saidikaV3.requestModels;

import lombok.Data;

@Data
public class updateProfile {
    private String password;
    private String phone_number;
    private String location;
}
