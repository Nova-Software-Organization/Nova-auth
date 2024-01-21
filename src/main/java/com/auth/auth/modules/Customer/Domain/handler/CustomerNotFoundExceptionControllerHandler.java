/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Customer.Domain.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth.auth.modules.Customer.Domain.exception.CustomerNotFoundException;

@ControllerAdvice
public class CustomerNotFoundExceptionControllerHandler {
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleClientNotFoundException(CustomerNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
