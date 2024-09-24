package com.shaul.saidikaV3.controllers;


import com.shaul.saidikaV3.entities.offered_services;
import com.shaul.saidikaV3.requestModels.offered_services_model;
import com.shaul.saidikaV3.responsemodels.add_service_reponse;
import com.shaul.saidikaV3.services.offered_services_service;
import com.shaul.saidikaV3.services.service_provider_service;
import com.shaul.saidikaV3.utils.GeneralUtils;

import jakarta.validation.Valid;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/services")
public class offered_services_controller {

    @Autowired
    offered_services_service oss;
    @Autowired
    service_provider_service sps;
    

    @PreAuthorize("hasAuthority('SERVICE_PROVIDER')")
    @PostMapping("/add_new_service")
    public ResponseEntity<add_service_reponse> add_new_service( @RequestBody @Valid offered_services_model osm,BindingResult bindingResult){
if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new add_service_reponse(GeneralUtils.createErrorMessage(bindingResult)));
        else

        // service_provider sp=sps.find_by_id(id).orElse(null);
        // offered_services offeredServices=new offered_services();
        // offeredServices.setName(osm.getName());
        // offeredServices.setDescription(osm.getDescription());
        // offeredServices.setProvider(sp);
        return  oss.add_service(osm);

    }

    @PreAuthorize("hasAuthority('SERVICE_PROVIDER')")
    @GetMapping("/get_services_offered")
    public ResponseEntity<List<offered_services>> getServicesOffered (){
    //   service_provider serviceProvider= provider_service.find_by_id(id).orElse(null);
    //    if (serviceProvider.getOfferedServices().isEmpty()) {
    //        return new ResponseEntity<>("No services", HttpStatus.NOT_FOUND);

    //    }else{
    //        return  ResponseEntity.ok(serviceProvider.getOfferedServices());
    //    }
        return oss.my_services();
   }


}
