package com.shaul.saidikaV3.exceptions;



public class EmailAlreadyRegisteredException extends RuntimeException{
private String message;

public  EmailAlreadyRegisteredException(){}

    public EmailAlreadyRegisteredException(String msg) {
        super(msg);
        this.message=msg;
    }


}
