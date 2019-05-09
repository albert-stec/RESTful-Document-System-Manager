package com.stecalbert.restfuldms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DocumentNotFoundException extends RuntimeException {
    private Object[] params;

    public DocumentNotFoundException(String message) {

        super(message);
    }

    public DocumentNotFoundException(String message, Object... params) {
        super(message);
        this.params = params;
    }

    public Object[] getParams() {
        return params;
    }
}
