package com.shaul.saidikaV3.controllers;

import com.shaul.saidikaV3.emaildetails;
import com.shaul.saidikaV3.services.email_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private email_service emailService;
    @PostMapping("/sendMail")
    public ResponseEntity<?>
    sendMail(@RequestBody emaildetails details)
    {
        return emailService.sendSimpleMail(details);


    }
}
