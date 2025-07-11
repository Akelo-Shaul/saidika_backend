package com.shaul.saidikaV3.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shaul.saidikaV3.auth.AuthService;
import com.shaul.saidikaV3.entities.offered_services;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.repositories.offered_services_repository;
import com.shaul.saidikaV3.requestModels.offered_services_model;
import com.shaul.saidikaV3.responsemodels.add_service_reponse;

@Service
public class offered_services_service {
    @Autowired
    offered_services_repository osr;

   


    @Autowired
    private  AuthService authService;

    public ResponseEntity<add_service_reponse> add_service(offered_services_model os){
        service_provider sp_guy=(service_provider) authService.getActiveProfile();
      offered_services osy=offered_services.builder()
                            .description(os.getDescription())
                            .name(os.getName())
                            .provider(sp_guy)
                            .average_rating(1)
                            .availableLocation(sp_guy.getAvailableLocation())
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
    public ResponseEntity<List<offered_services>> all_services(){
      return ResponseEntity.ok(osr.findAll());

    }

    public ResponseEntity<List<offered_services>> filter_services_by_location(String location){
      return ResponseEntity.ok(osr.findByAvailableLocation(location));

    }
    

public String update_all(List<offered_services> hj){
        osr.saveAllAndFlush(hj);
        return "success";
}




}
