package com.xenon.core.domain.exception;

import com.xenon.core.domain.model.ResponseMessage;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class BaseException extends RuntimeException {

    private ResponseMessage responseMessage;

    public BaseException(@NonNull ResponseMessage responseMessage) {
        super(responseMessage.getMessage());
        this.responseMessage = responseMessage;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Exception e) {
        super(e);
    }
}
