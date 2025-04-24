package com.shaul.saidikaV3.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shaul.saidikaV3.entities.MpesaCallback;
import java.util.Optional;




@Repository
public interface MpesaCallbackRepository extends JpaRepository<MpesaCallback, UUID> {

 Optional<MpesaCallback> findByCheckoutRequestID(String checkoutRequestID);
}
