package com.stecalbert.restfuldms.exception;

public class WithParamsException extends RuntimeException {
    private Object[] params;

    public WithParamsException(String message) {
        super(message);
    }

    public WithParamsException(String message, Object... params) {
        super(message);
        this.params = params;
    }

    public Object[] getParams() {
        return params;
    }
}
