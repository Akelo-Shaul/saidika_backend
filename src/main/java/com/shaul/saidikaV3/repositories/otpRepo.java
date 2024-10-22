package com.shaul.saidikaV3.repositories;

import com.shaul.saidikaV3.entities.otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface otpRepo extends JpaRepository<otp,String> {

    @Query("select r from otp r where r.otpToken=?1")
    Optional<otp> findByOtpToken(String otpcode);
}
