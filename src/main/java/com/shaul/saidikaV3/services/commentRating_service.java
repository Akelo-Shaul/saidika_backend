package com.shaul.saidikaV3.services;

import com.shaul.saidikaV3.entities.commentRating;
import com.shaul.saidikaV3.repositories.comment_rating_repo;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class commentRating_service {
  @Autowired
  comment_rating_repo crp;

    public commentRating make_review(commentRating cr){
      return  crp.save(cr);
    }



}
