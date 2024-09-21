package com.shaul.saidikaV3.services;

import com.shaul.saidikaV3.entities.offered_services;
import com.shaul.saidikaV3.repositories.offered_services_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class offered_services_service {
    @Autowired
    offered_services_repository osr;



    public offered_services add_service(offered_services os){
       return osr.save(os);
    }
    public Optional<offered_services> find_service_by_id(UUID id){
        return osr.findById(id);
    }



}
