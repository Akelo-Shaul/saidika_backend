package com.shaul.saidikaV3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.requestModels.loginRequestmodel;
import com.shaul.saidikaV3.requestModels.registerRequestModel;
import com.shaul.saidikaV3.responsemodels.login_response;
import com.shaul.saidikaV3.services.service_provider_service;
import com.shaul.saidikaV3.utils.GeneralUtils;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/provider")
@RestController
public class service_provider_controller {
          @Autowired
     service_provider_service provider_service;


    @PostMapping("/register")
    public ResponseEntity<String> add_provider(@RequestBody  @Valid registerRequestModel provider_request_model,BindingResult bindingResult){
      if(bindingResult.hasErrors())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GeneralUtils.createErrorMessage(bindingResult));
        else
   

      return  provider_service.registerProvider(provider_request_model);
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


}
