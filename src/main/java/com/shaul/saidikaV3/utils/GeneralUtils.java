package com.shaul.saidikaV3.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GeneralUtils {
  private static ObjectMapper objectMapper=new ObjectMapper();
    public static String createErrorMessage(BindingResult result) {
        try {
            List<FieldError> fieldErrors = result.getFieldErrors();


            Map<String, String> fieldErrorMap = new HashMap<>();
            for (FieldError fError : fieldErrors) {
                fieldErrorMap.put(fError.getField(), fError.getDefaultMessage());
            }

            Map<String, Map<String,String>> errorMessageMap = new HashMap<>();
            errorMessageMap.put("fielderrors", fieldErrorMap);


            return objectMapper.writeValueAsString(errorMessageMap);

//            return result.getAllErrors().toString();

        } catch (Exception e) {
            // Handle the exception (e.g., log it) or throw a more specific exception
            return ""; // Return an empty string or handle the error as needed
        }
    }
}
