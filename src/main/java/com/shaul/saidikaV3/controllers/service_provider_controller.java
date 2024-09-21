package com.shaul.saidikaV3.controllers;

import com.shaul.saidikaV3.entities.offered_services;
import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.services.service_finder_service;
import com.shaul.saidikaV3.services.service_provider_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/provider")
@RestController
public class service_provider_controller {
    @Autowired
    service_provider_service provider_service;


    @PostMapping()
    public service_provider addFinder(@RequestBody service_provider provider_request_model){
        return  provider_service.add_provider(provider_request_model);
    }

    @GetMapping() //TODO remove this nonsense
    public List<service_provider> get_allProviders(){
        return provider_service.get_all_providers();
    }

   @GetMapping("/get_services_offered/{id}")
    public ResponseEntity<?> getServicesOffered (@PathVariable UUID id){
      service_provider serviceProvider= provider_service.find_by_id(id).orElse(null);
       if (serviceProvider.getOfferedServices().isEmpty()) {
           return new ResponseEntity<>("No services", HttpStatus.NOT_FOUND);

       }else{
           return  ResponseEntity.ok(serviceProvider.getOfferedServices());
       }

   }



}
