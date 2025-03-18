package com.xenon.core.domain.exception;

public class ApiException extends BaseException {
    public ApiException(Exception e) {
        super(e);
    }
}
