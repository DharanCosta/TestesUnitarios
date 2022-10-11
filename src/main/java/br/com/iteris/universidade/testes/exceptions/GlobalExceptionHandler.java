package br.com.iteris.universidade.testes.exceptions;

import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Setter
    @Getter
    private static class JsonResponse {
        String message;

        public JsonResponse(String message) {
            super();
            this.message = message;
        }

    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<JsonResponse> handleException(Exception e) {
        return new ResponseEntity<>(new JsonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}