package com.shaul.saidikaV3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class errorResponse {

    private int statusCode;
    private String message;

    public errorResponse(String message)
    {
        super();
        this.message = message;
    }
}
