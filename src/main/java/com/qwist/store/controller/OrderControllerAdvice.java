package com.qwist.store.controller;

import com.qwist.store.exception.CustomerRestrictedActionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/***
 * @author - Kiryl Karpuk
 */
@Slf4j
@RestControllerAdvice
public class OrderControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerRestrictedActionException.class)
    public ResponseEntity<String> handleCustomerRestrictedActionException(CustomerRestrictedActionException ex) {
        logger.error(ex);
        return ResponseEntity
                .status(403)
                .body("Customer restricted action: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logger.error(ex);
        return ResponseEntity
                .status(500)
                .body("An unexpected error occurred: " + ex.getMessage());
    }

}
