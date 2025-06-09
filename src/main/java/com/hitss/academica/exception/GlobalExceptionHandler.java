package com.hitss.academica.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Anotación clave: intercepta excepciones de todos los @RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Este método se activa cuando una validación de @Valid falla
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("error", "Validation Failed");
        body.put("messages", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Aquí puedes añadir más manejadores para otras excepciones
    // Por ejemplo, para el RuntimeException("Usuario no encontrado")
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Resource Not Found or Business Logic Error");
        body.put("message", ex.getMessage());
        // Podríamos devolver 404 si el mensaje contiene "not found", o 400 para otros casos.
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}