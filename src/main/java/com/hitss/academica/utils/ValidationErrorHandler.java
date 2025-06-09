package com.hitss.academica.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class ValidationErrorHandler {

    public static ResponseEntity<Map<String, String>> handle(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            // Usamos el mensaje por defecto que definimos en las anotaciones del DTO
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}