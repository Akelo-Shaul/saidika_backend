package com.shaul.saidikaV3.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MpesaCallbackController {

    private Map<String, Object> lastResponse;

    @PostMapping("/callback")
    public ResponseEntity<?> receiveCallback(@RequestBody Map<String, Object> payload) {
        try {
            String prettyJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
            System.out.println("=== M-Pesa Callback Received ===");
            System.out.println(prettyJson);
        } catch (Exception e) {
            System.out.println("Error printing JSON: " + e.getMessage());
        }

        lastResponse = payload;

        return ResponseEntity.ok(payload);

        // return ResponseEntity.ok(Map.of(
        //         "ResultCode", 0,
        //         "ResultDesc", "Callback received successfully"
        // ));
    }

    // @GetMapping("/last-response")
    // public ResponseEntity<Object> getLastResponse() {
    //     if (lastResponse != null) {
    //         return ResponseEntity.ok(Map.of("success", true, "data", lastResponse));
    //     } else {
    //         return ResponseEntity.ok(Map.of("success", false, "message", "No callback yet"));
    //     }
    // }
}
