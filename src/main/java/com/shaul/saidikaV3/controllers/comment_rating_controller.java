package com.shaul.saidikaV3.controllers;

import com.shaul.saidikaV3.entities.commentRating;
import com.shaul.saidikaV3.entities.offered_services;
import com.shaul.saidikaV3.entities.service_finder;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.requestModels.commentRatingModel;
import com.shaul.saidikaV3.services.commentRating_service;
import com.shaul.saidikaV3.services.offered_services_service;
import com.shaul.saidikaV3.services.service_finder_service;
import com.shaul.saidikaV3.services.service_provider_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comments")
public class comment_rating_controller {
    @Autowired
    commentRating_service crs;
    @Autowired
    offered_services_service Oss;
    @Autowired
    service_finder_service serfs;
    @Autowired
    service_provider_service serps;

    @PostMapping("/{commenter_id}/{service_id}")
    public commentRating add_review(@PathVariable UUID commenter_id, @PathVariable UUID service_id, @RequestBody commentRatingModel crm){
    commentRating cr=new commentRating();
    offered_services ors = Oss.find_service_by_id(service_id).orElse(null);
    service_finder sff= serfs.find_by_id(commenter_id).orElse(null);
    service_provider spp=serps.find_by_id(commenter_id).orElse(null);

    cr.setComment(crm.getComment());
    cr.setRating(crm.getRating());
    cr.setOffer_services(ors);


    if (sff != null && spp == null){
        cr.setCommenter(sff);
    } else if (spp != null && sff == null) {
        cr.setCommenter(spp);
    }

    return crs.make_review(cr);

    }



}
