package com.shaul.saidikaV3.services;

import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.repositories.service_finder_repo;
import com.shaul.saidikaV3.repositories.service_provider_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class service_provider_service {
    @Autowired
    service_provider_repo spr;
    service_provider sp=new service_provider();

    public service_provider add_provider(service_provider sp){
        return spr.save(sp);
    }

    public Optional<service_provider> find_by_id(UUID id){
        return spr.findById(id);
    }

    public List<service_provider> get_all_providers() {
        return spr.findAll();
    }


}
