package com.shaul.saidikaV3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaul.saidikaV3.entities.comment_rating;
import com.shaul.saidikaV3.repositories.comment_rating_repo;

@Service
public class commentRating_service {
  @Autowired
  comment_rating_repo crp;

    public comment_rating make_review(comment_rating cr){
     
     
    return crp.saveAndFlush(cr);
    

    }

 
}
