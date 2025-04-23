package com.shaul.saidikaV3.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController

public class MpesaCallbackController {

    private static final Logger logger = LoggerFactory.getLogger(MpesaCallbackController.class);
    private final Map<String, Map<String, Object>> responseStore = new ConcurrentHashMap<>(); // Thread-safe storage

    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> receiveCallback(@RequestBody Map<String, Object> payload) {
        try {
            logger.info("M-Pesa Callback Received: {}", payload);

            // Validate payload structure
            if (payload == null || !payload.containsKey("Body")) {
                logger.error("Invalid callback payload: Missing 'Body'");
                return ResponseEntity.badRequest().body(Map.of(
                        "ResultCode", 1,
                        "ResultDesc", "Invalid payload"
                ));
            }

            // Extract relevant fields (e.g., CheckoutRequestID for storage)
            @SuppressWarnings("unchecked")
            Map<String, Object> body = (Map<String, Object>) payload.get("Body");
            @SuppressWarnings("unchecked")
            Map<String, Object> stkCallback = (Map<String, Object>) body.get("stkCallback");
            String checkoutRequestID = (String) stkCallback.get("CheckoutRequestID");

            if (checkoutRequestID == null) {
                logger.error("Missing CheckoutRequestID in callback payload");
                return ResponseEntity.badRequest().body(Map.of(
                        "ResultCode", 1,
                        "ResultDesc", "Missing CheckoutRequestID"
                ));
            }

            // Store the response by CheckoutRequestID
            responseStore.put(checkoutRequestID, payload);
            logger.info("Stored callback for CheckoutRequestID: {}", checkoutRequestID);

            return ResponseEntity.ok(Map.of(
                    "ResultCode", 0,
                    "ResultDesc", "Callback received successfully"
            ));
        } catch (Exception e) {
            logger.error("Error processing callback: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of(
                    "ResultCode", 1,
                    "ResultDesc", "Error processing callback"
            ));
        }
    }

    @GetMapping("/last-response/{checkoutRequestID}")
    public ResponseEntity<Map<String, Object>> getLastResponse(@PathVariable String checkoutRequestID) {
        try {
            Map<String, Object> lastResponse = responseStore.get(checkoutRequestID);
            if (lastResponse != null) {
                logger.info("Retrieved response for CheckoutRequestID: {}", checkoutRequestID);
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "data", lastResponse
                ));
            } else {
                logger.warn("No callback found for CheckoutRequestID: {}", checkoutRequestID);
                return ResponseEntity.ok(Map.of(
                        "success", false,
                        "message", "No callback found for the provided CheckoutRequestID"
                ));
            }
        } catch (Exception e) {
            logger.error("Error retrieving last response: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Error retrieving response"
            ));
        }
    }
}