package com.felipeAlves.ecommerce_api.exception.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.felipeAlves.ecommerce_api.exception.EmailJaCadastradoException;
import com.felipeAlves.ecommerce_api.exception.EstoqueMinimoException;
import com.felipeAlves.ecommerce_api.exception.UsuarioJaExisteException;
import com.felipeAlves.ecommerce_api.exception.UsuarioNaoPermitidoException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioJaExisteException.class)
    public ResponseEntity<String> handleUsuarioJaExisteException(UsuarioJaExisteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage()); // HTTP 409 Conflict
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<String> handleEmailJaExisteException(EmailJaCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage()); // HTTP 409 Conflict
    }

    @ExceptionHandler(UsuarioNaoPermitidoException.class)
    public ResponseEntity<String> handleUsuarioNaoPermitidoException(UsuarioNaoPermitidoException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage()); // HTTP 403 Forbidden
    }

    @ExceptionHandler(EstoqueMinimoException.class)
    public ResponseEntity<String> handleEstoqueMinimoException(EstoqueMinimoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); // HTTP 400 Bad Request
    }
}
