package com.shaul.saidikaV3.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MpesaCallbackController {


    private Map<String, Object> lastResponse;

    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> receiveCallback(@RequestBody Map<String, Object> payload) {
        System.out.println("M-Pesa Callback Received:\n" + payload);
        lastResponse = payload;
        return ResponseEntity.ok(Map.of(
                "ResultCode", 0,
                "ResultDesc", "Callback received successfully"
        ));
    }

    @GetMapping("/last-response")
    public ResponseEntity<Object> getLastResponse() {
        if (lastResponse != null) {
            return ResponseEntity.ok(Map.of("success", true, "data", lastResponse));
        } else {
            return ResponseEntity.ok(Map.of("success", false, "message", "No callback yet"));
        }
    }

}
