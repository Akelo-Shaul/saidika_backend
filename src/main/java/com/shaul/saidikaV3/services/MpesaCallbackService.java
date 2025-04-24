package com.shaul.saidikaV3.services;


import org.springframework.stereotype.Service;


import com.shaul.saidikaV3.entities.MpesaCallback;
import com.shaul.saidikaV3.repositories.MpesaCallbackRepository;
import com.shaul.saidikaV3.requestModels.mpesacallback;



@Service
public class MpesaCallbackService {
    

    @AutoWired
    private MpesaCallbackRepository mpesaCallbackRepository;


    public String save_info(mpesacallback callbackJson){

      

      return  callbackJson.getBody().getStkCallback().getMerchantRequestID();
    }

    public MpesaCallback find_the_callback(String checkoutid){
    MpesaCallback mn=  mpesaCallbackRepository.findByCheckoutRequestID(checkoutid).orElse(null);
      return mn;
    }



    }

