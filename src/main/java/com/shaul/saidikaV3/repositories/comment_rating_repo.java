package com.shaul.saidikaV3.repositories;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shaul.saidikaV3.entities.comment_rating;


public interface comment_rating_repo extends JpaRepository<comment_rating, UUID> {

 

}
