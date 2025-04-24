package com.shaul.saidikaV3.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shaul.saidikaV3.entities.MpesaCallback;
import com.shaul.saidikaV3.repositories.MpesaCallbackRepository;
import com.shaul.saidikaV3.requestModels.Body;
import com.shaul.saidikaV3.requestModels.mpesacallback;
import com.shaul.saidikaV3.services.AutoWired;
import com.shaul.saidikaV3.services.MpesaCallbackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class MpesaCallbackController {
    
      @Autowired
      private MpesaCallbackRepository mpr;

    @PostMapping("/callback")
    public ResponseEntity<?> handleMpesaCallback(@RequestBody mpesacallback callbackJson) {
        MpesaCallback mpb=MpesaCallback.builder()
        .merchantRequestID(callbackJson.getBody().getStkCallback().getMerchantRequestID())
        .checkoutRequestID(callbackJson.getBody().getStkCallback().getCheckoutRequestID())
        .resultCode(callbackJson.getBody().getStkCallback().getResultCode())
        .resultDesc(callbackJson.getBody().getStkCallback().getResultDesc())
        .build();

        return ResponseEntity.ok(mpr.save(mpb));
    }

@GetMapping("/last-response/{checkoutRequestID}")
public MpesaCallback getMethodName(@PathVariable String checkoutRequestID) {
    return mpr.findByCheckoutRequestID(checkoutRequestID).orElse(null);
}



}
