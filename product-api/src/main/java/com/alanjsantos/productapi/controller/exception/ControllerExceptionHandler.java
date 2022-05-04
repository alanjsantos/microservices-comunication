package com.alanjsantos.productapi.controller.exception;

import com.alanjsantos.productapi.service.exception.AuthenticationException;
import com.alanjsantos.productapi.service.exception.DataIntegrityException;
import com.alanjsantos.productapi.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity (DataIntegrityException e){
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),  e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandardError> authentication (AuthenticationException e){
        StandardError err = new StandardError(HttpStatus.UNAUTHORIZED.value(),  e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Error de validação");

        for (FieldError x : e.getBindingResult().getFieldErrors()){
            err.addError(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
