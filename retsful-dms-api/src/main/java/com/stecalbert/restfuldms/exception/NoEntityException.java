package com.stecalbert.restfuldms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoEntityException extends WithParamsException {
    public NoEntityException(String message) {
        super(message);
    }

    public NoEntityException(String message, Object... params) {
        super(message, params);
    }
}
