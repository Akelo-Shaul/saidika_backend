package com.shaul.saidikaV3.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MpesaCallbackController {

    private Map<String, Object> lastCallbackResponse;

    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> receiveCallback(@RequestBody Map<String, Object> payload) {
        System.out.println("M-Pesa Callback Received:\n" + payload);
        lastCallbackResponse = payload;
        return ResponseEntity.ok(Map.of(
                "ResultCode", 0,
                "ResultDesc", "Callback received successfully"
        ));
    }

    @GetMapping("/last-response")
    public ResponseEntity<Map<String, Object>> getLastResponse() {
        if (lastCallbackResponse != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", lastCallbackResponse);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "No callback received yet.");
            return ResponseEntity.ok(response);
        }
    }
}