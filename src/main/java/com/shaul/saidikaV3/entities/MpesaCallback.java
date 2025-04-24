package com.shaul.saidikaV3.entities;


import java.util.UUID;

import org.hibernate.annotations.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class MpesaCallback {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

   
    private String merchantRequestID;

   
    private String checkoutRequestID;

    
    private Integer resultCode;

   
    private String resultDesc;
    
}