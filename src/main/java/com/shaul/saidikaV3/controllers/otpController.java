package com.shaul.saidikaV3.controllers;


import com.shaul.saidikaV3.emaildetails;
import com.shaul.saidikaV3.entities.otp;
import com.shaul.saidikaV3.exceptions.EmailAlreadyRegisteredException;
import com.shaul.saidikaV3.exceptions.errorResponse;
import com.shaul.saidikaV3.services.email_service;
import com.shaul.saidikaV3.services.otpservice;
import com.shaul.saidikaV3.services.service_finder_service;
import com.shaul.saidikaV3.services.service_provider_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class otpController {
    @Autowired
    otpservice oService;
    @Autowired
    email_service e_service;
    @Autowired
    service_provider_service sd;
    @Autowired
    service_finder_service sdf;


    @PostMapping("/verify_otp")
    public ResponseEntity<?>
    verification(@RequestParam String code){
        return oService.verify_otp(code);



    }
    @PostMapping("/send_otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email){
     findEmail(email);
        String code=otpgenerator();
        emaildetails ed= emaildetails.builder()
                .recipient(email)
                .subject("verification code")
                .msgBody(code)
                .build();
        otp new_otp= otp.builder()
                .email(email)
                .otpToken(code)
                .build();
        oService.add_otp(new_otp);
        return  e_service.sendSimpleMail(ed);


    }

    public String otpgenerator(){
        Random rand = new Random();
        int rand_int = rand.nextInt(1000000);
        String otp= String.format("%06d", rand_int);;
        return otp;
    }
  public void findEmail(String emm){
      String message="Account with Email Already exists";
        if(sd.find_by_email(emm) != null || sd.find_by_email(emm) != null)
            throw new EmailAlreadyRegisteredException(message);
  }
    @ExceptionHandler(value = EmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public errorResponse handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex) {
        return new errorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
}
