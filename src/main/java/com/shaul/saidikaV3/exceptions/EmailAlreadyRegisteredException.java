package com.shaul.saidikaV3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class EmailAlreadyRegisteredException extends RuntimeException{
private String message;

public  EmailAlreadyRegisteredException(){}

    public EmailAlreadyRegisteredException(String msg) {
        super(msg);
        this.message=msg;
    }


}
