package com.stecalbert.restfuldms.exception;

public class NotFoundException extends RuntimeException {
    private Object[] params;

    public NotFoundException(String message) {

        super(message);
    }

    public NotFoundException(String message, Object... params) {
        super(message);
        this.params = params;
    }

    public Object[] getParams() {
        return params;
    }
}
