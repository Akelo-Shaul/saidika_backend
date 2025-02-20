package com.shaul.saidikaV3.controllers;

import com.shaul.saidikaV3.entities.Users;
import com.shaul.saidikaV3.entities.service_provider;
import com.shaul.saidikaV3.requestModels.NotificationRequest;
import com.shaul.saidikaV3.responsemodels.NotificationResponse;
import com.shaul.saidikaV3.services.FCMService;
import com.shaul.saidikaV3.services.service_finder_service;
import com.shaul.saidikaV3.services.service_provider_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class NotificationController {

    @Autowired
    private FCMService fcmService;
    @Autowired
    service_provider_service provider_service;
    @Autowired
    service_finder_service finder_service;

    @PostMapping("/notification")
    public ResponseEntity sendNotification(@RequestBody NotificationRequest request) throws ExecutionException, InterruptedException, ExecutionException {
        fcmService.sendMessageToToken(request);
        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @GetMapping("/getNotificationToken")
    public String getNotificationTok(@RequestParam("ReceiverID") UUID id) {
        String token;
        if(provider_service.find_by_id(id).isPresent()){
            token=provider_service.find_by_id(id).get().getNotif_token();
        } else{
            token = provider_service.find_by_id(id).get().getNotif_token();
        }
        return token;
    }




}
