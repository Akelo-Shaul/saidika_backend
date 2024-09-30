package com.shaul.saidikaV3.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shaul.saidikaV3.entities.offered_services;
import com.shaul.saidikaV3.entities.service_provider;

public interface offered_services_repository extends JpaRepository<offered_services, UUID> {

     @Query("select r from offered_services r where r.provider=?1")
     List<offered_services> findByProvider(service_provider activeProfile);

     List<offered_services> findByAvailableLocation(String loc);

//       @Query("select r from comment_rating r where r.offerservices=?1")
//    Optional< List<comment_rating> > findByReviews(offered_services sr);

//    @Query("select r from offered_services r where r.provider=?1")
//      List<offered_services> findByProvider(service_provider activeProfile);

}
