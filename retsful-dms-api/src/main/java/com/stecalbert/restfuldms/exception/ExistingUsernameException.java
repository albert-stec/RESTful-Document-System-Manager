package com.stecalbert.restfuldms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistingUsernameException extends RuntimeException {

    public ExistingUsernameException(String message) {
        super(message);
    }
}
