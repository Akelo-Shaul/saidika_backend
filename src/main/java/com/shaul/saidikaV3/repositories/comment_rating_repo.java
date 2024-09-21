package com.shaul.saidikaV3.repositories;


import com.shaul.saidikaV3.entities.commentRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface comment_rating_repo extends JpaRepository<commentRating, UUID> {
}
