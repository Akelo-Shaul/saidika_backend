package com.shaul.saidikaV3.repositories;

import com.shaul.saidikaV3.entities.offered_services;
import com.shaul.saidikaV3.entities.service_provider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface offered_services_repository extends JpaRepository<offered_services, UUID> {

     @Query("select r from offered_services r where r.provider=?1")
     List<offered_services> findByProvider(service_provider activeProfile);


}
