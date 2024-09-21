package com.shaul.saidikaV3.services;
import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.repositories.service_finder_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class service_finder_service {
    @Autowired
    service_finder_repo sfr;

    service_finder sf=new service_finder();

    public service_finder add_finder(service_finder sf){
       return sfr.save(sf);
    }

    public Optional<service_finder> find_by_id(UUID id){
            return sfr.findById(id);
    }

    public List<service_finder> get_all() {
        return sfr.findAll();
    }

    }
