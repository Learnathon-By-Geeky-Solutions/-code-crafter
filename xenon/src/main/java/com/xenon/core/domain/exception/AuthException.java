package com.xenon.core.domain.exception;

import com.xenon.core.domain.model.ResponseMessage;

public class AuthException extends BaseException {
    public AuthException(String message) {
        super(message);
    }

    public AuthException(ResponseMessage responseMessage) {
        super(responseMessage);
    }
}
