package com.shaul.saidikaV3.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shaul.saidikaV3.auth.AuthService;
import com.shaul.saidikaV3.entities.Users;
import com.shaul.saidikaV3.entities.comment_rating;
import com.shaul.saidikaV3.entities.offered_services;
import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.requestModels.commentRatingModel;
import com.shaul.saidikaV3.services.commentRating_service;
import com.shaul.saidikaV3.services.offered_services_service;

@RestController
@RequestMapping("/api/v1/comments")
public class comment_rating_controller {
    @Autowired
    commentRating_service crs;
    @Autowired
    offered_services_service Oss;
    @Autowired
    AuthService cAuthService;



    @PreAuthorize("hasAuthority('FINDER') or hasAuthority('PROVIDER')")
    @PostMapping("/{service_id}")
    public comment_rating add_review(@PathVariable UUID service_id, @RequestBody commentRatingModel crm){
    comment_rating cr=new comment_rating();
    offered_services ors = Oss.find_service_by_id(service_id).orElse(null);
    Integer a=ors.getAverage_rating();
    Integer b=crm.getRating();
    Integer c= a+b;
    Integer avg_rate=c/2;
    ors.setAverage_rating(avg_rate);
     Users person=cAuthService.getActiveProfile();
   
    cr.setComment(crm.getComment());
    cr.setRating(crm.getRating());
    cr.setOfferservices(ors);
    if (person!=null&&person instanceof service_provider){
        cr.setCommenter(person);
    } else if (person!=null&&person instanceof service_finder) {
        cr.setCommenter(person);
    }

   
   return crs.make_review(cr); 
  
    }

   

}
