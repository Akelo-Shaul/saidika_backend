package com.shaul.saidikaV3.controllers;


import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.exceptions.EmailAlreadyRegisteredException;
import com.shaul.saidikaV3.exceptions.errorResponse;
import com.shaul.saidikaV3.requestModels.loginRequestmodel;
import com.shaul.saidikaV3.requestModels.registerRequestModel;
import com.shaul.saidikaV3.responsemodels.login_response;
import com.shaul.saidikaV3.services.service_finder_service;
import com.shaul.saidikaV3.utils.GeneralUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/finder")
@RestController
public class service_finder_controller {
    @Autowired
    service_finder_service finder_service;
   

    @PostMapping("/login")
    public ResponseEntity<login_response> login(@RequestBody @Valid loginRequestmodel loginRequest, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(login_response.builder().message(GeneralUtils.createErrorMessage(bindingResult)).build());
        else
            return finder_service.login(loginRequest);
    }

    @PreAuthorize("hasAuthority('FINDER')")
    @GetMapping("/activeUser")
    public ResponseEntity<service_finder> activeUser()
    {
        return finder_service.activeUser();
    }


    @PostMapping("/register")
    public ResponseEntity<String> addFinder(@RequestPart("first_name") String fn,@RequestPart("last_name") String ln,@RequestPart("email")  String eml,@RequestPart("phone") String pn,@RequestPart("password") String pass,@RequestPart("location") String loc,@RequestPart("role") String rol, @RequestPart(value = "profile_pic",required = false)MultipartFile dp,@RequestPart("notifTok") String notifTok) throws IOException {
        registerRequestModel finder_request_model= new registerRequestModel().builder()
                .last_name(ln)
                .first_name(fn)
                .email(eml)
                .role(rol)
                .phone(pn)
                .location(loc)
                .password(pass)
                .notifTok(notifTok)
                .build();

            return  finder_service.registerFinder(finder_request_model,dp);
    }

    @GetMapping() 
    public List<service_finder> get_all_finders(){
     return finder_service.get_all();
    }
  @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest)
    {
        return finder_service.logOut(httpServletRequest);
    }
    @ExceptionHandler(value = EmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public errorResponse handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex) {
        return new errorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }



    @PostMapping("/updateProfilePhoto")
    public String updatePhoto(@RequestPart(value = "image")MultipartFile profile_pic) throws IOException {
     return finder_service.update_profilePhoto(profile_pic);
    }
   @GetMapping("/getpp")
    public ResponseEntity<?> getpp() throws IOException {
        return finder_service.get_profilePhoto();
   }



    @PostMapping("/updatetoken")
    public String updateToken(@RequestBody String token)
    {

            return finder_service.update_token(token);
    }


}
