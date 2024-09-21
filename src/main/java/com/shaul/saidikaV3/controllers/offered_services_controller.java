package com.shaul.saidikaV3.controllers;

import com.shaul.saidikaV3.entities.offered_services;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.requestModels.offered_services_model;
import com.shaul.saidikaV3.services.offered_services_service;
import com.shaul.saidikaV3.services.service_provider_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services")
public class offered_services_controller {

    @Autowired
    offered_services_service oss;
    @Autowired
    service_provider_service sps;



    @PostMapping("{id}")
    public offered_services add_new_service(@PathVariable UUID id, @RequestBody offered_services_model osm){

        service_provider sp=sps.find_by_id(id).orElse(null);
        offered_services offeredServices=new offered_services();
        offeredServices.setName(osm.getName());
        offeredServices.setDescription(osm.getDescription());
        offeredServices.setProvider(sp);
        return  oss.add_service(offeredServices);

    }




}
