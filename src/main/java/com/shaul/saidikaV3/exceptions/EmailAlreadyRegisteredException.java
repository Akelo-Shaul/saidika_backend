package com.shaul.saidikaV3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "Email already Registered")
public class EmailAlreadyRegisteredException extends RuntimeException{
}
