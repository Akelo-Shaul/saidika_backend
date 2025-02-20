package com.shaul.saidikaV3.controllers;

import java.io.IOException;
import java.util.List;

import com.shaul.saidikaV3.exceptions.EmailAlreadyRegisteredException;
import com.shaul.saidikaV3.exceptions.errorResponse;
import com.shaul.saidikaV3.requestModels.updateProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.requestModels.loginRequestmodel;
import com.shaul.saidikaV3.requestModels.registerRequestModel;
import com.shaul.saidikaV3.responsemodels.login_response;
import com.shaul.saidikaV3.services.service_provider_service;
import com.shaul.saidikaV3.utils.GeneralUtils;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/provider")
@RestController
public class service_provider_controller {
          @Autowired
     service_provider_service provider_service;


    @PostMapping("/register")
    public ResponseEntity<String> add_provider(@RequestPart("first_name") String fn,@RequestPart("last_name") String ln,@RequestPart("email")  String eml,@RequestPart("phone") String pn,@RequestPart("password") String pass,@RequestPart("location") String loc,@RequestPart("role") String rol, @RequestPart(value = "profile_pic",required = false)MultipartFile fg,@RequestPart("notifTok") String notifTok) throws IOException {
        registerRequestModel provider_request_model= new registerRequestModel().builder()
                .last_name(ln)
                .first_name(fn)
                .email(eml)
                .role(rol)
                .phone(pn)
                .location(loc)
                .password(pass)
                .notifTok(notifTok)
                .build();
        return  provider_service.registerProvider(provider_request_model,fg);
    }

    @GetMapping() 
    public List<service_provider> get_allProviders(){
        return provider_service.get_all_providers();
    }

    @PreAuthorize("hasAuthority('PROVIDER')")
    @GetMapping("/activeUser")
    public ResponseEntity<service_provider> activeUser()
    {
        return provider_service.activeUser();
    }

@PostMapping("/login")
    public ResponseEntity<login_response> login(@RequestBody @Valid loginRequestmodel loginRequest, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(login_response.builder().message(GeneralUtils.createErrorMessage(bindingResult)).build());
        else
            return provider_service.login(loginRequest);
    }
    @PreAuthorize("hasAuthority('PROVIDER')")
    @PostMapping("/update_profile")
    public ResponseEntity<String> profile_update(@RequestBody updateProfile up){
        return provider_service.update_profile(up);

    }
    @ExceptionHandler(value = EmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public errorResponse handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex) {
        return new errorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
    @PostMapping("/updateProfilePhoto")
    public String updatePhoto(@RequestPart("image")MultipartFile profile_pic) throws IOException {
        return provider_service.update_profilePhoto(profile_pic);
    }
    @GetMapping("/getpp")
    public ResponseEntity<?> getpp() throws IOException {
        return provider_service.get_profilePhoto();
    }
}
