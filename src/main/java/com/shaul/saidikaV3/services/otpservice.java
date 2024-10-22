package com.shaul.saidikaV3.services;

import com.shaul.saidikaV3.entities.otp;
import com.shaul.saidikaV3.repositories.otpRepo;
import com.shaul.saidikaV3.responsemodels.otpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class otpservice {
    @Autowired
    otpRepo oRepo;
    public ResponseEntity<?> verify_otp(String code){
       otp tkn= oRepo.findByOtpToken(code).orElse(null);
       if (tkn != null){
           oRepo.delete(tkn);

           return ResponseEntity.status(HttpStatus.OK).body(new otpResponse(HttpStatus.OK.value(), "verification success"));
       }else

           return ResponseEntity.status(HttpStatus.CONFLICT).body(new otpResponse(HttpStatus.CONFLICT.value(), "verification failed")) ;
    }
    public String add_otp(otp oTP){
        oRepo.save(oTP);
        return "success";
    }



}
