package com.shaul.saidikaV3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "email not found")
public class EmailNotFoundException extends RuntimeException{
}
