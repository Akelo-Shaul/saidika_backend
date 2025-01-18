package com.shaul.saidikaV3.responsemodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class otpResponse {
    private int statusCode;
    private String message;
}
