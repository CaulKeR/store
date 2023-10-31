package com.qwist.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/***
 * @author - Kiryl Karpuk
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CustomerRestrictedActionException extends RuntimeException {

    public CustomerRestrictedActionException(String message) {
        super(message);
    }

}
