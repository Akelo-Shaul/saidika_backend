package com.shaul.saidikaV3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shaul.saidikaV3.auth.AuthService;
import com.shaul.saidikaV3.entities.Users;
import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.services.service_finder_service;
import com.shaul.saidikaV3.services.service_provider_service;

@RestController
public class user_controller {
@Autowired
AuthService sAuthService;
@Autowired
service_finder_service sEs;
@Autowired
service_provider_service sPs;

 @PreAuthorize("hasAuthority('FINDER') or hasAuthority('PROVIDER')")
 @GetMapping("/active_User")
    public ResponseEntity<?> get_activeUser()
    {
       Users persoN=sAuthService.getActiveProfile();
        if(persoN!=null&&persoN instanceof service_provider)
            return ResponseEntity.ok((service_provider) persoN);
        else if(persoN!=null&&persoN instanceof service_finder)
            return ResponseEntity.ok((service_finder) persoN);
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }



        //return provider_service.activeUser();
    }







