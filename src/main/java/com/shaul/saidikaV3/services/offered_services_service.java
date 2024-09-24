package com.shaul.saidikaV3.services;

import com.shaul.saidikaV3.auth.AuthService;
import com.shaul.saidikaV3.entities.offered_services;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.repositories.offered_services_repository;
import com.shaul.saidikaV3.requestModels.offered_services_model;
import com.shaul.saidikaV3.responsemodels.add_service_reponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class offered_services_service {
    @Autowired
    offered_services_repository osr;

    @Autowired
    private  AuthService authService;

    public ResponseEntity<add_service_reponse> add_service(offered_services_model os){
      offered_services osy=offered_services.builder()
                            .description(os.getDescription())
                            .name(os.getName())
                            .provider((service_provider) authService.getActiveProfile())
                            .build();

                            osr.saveAndFlush(osy);
   return ResponseEntity.status(HttpStatus.CREATED).body(new add_service_reponse("service saved"));
       
    }
    public Optional<offered_services> find_service_by_id(UUID id){
        return osr.findById(id);
    }

    public ResponseEntity<List<offered_services>> my_services() {
        return ResponseEntity.ok(osr.findByProvider((service_provider)authService.getActiveProfile()));
    }

}
