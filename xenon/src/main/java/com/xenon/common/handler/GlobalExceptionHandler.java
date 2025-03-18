package com.xenon.common.handler;

import com.xenon.core.domain.exception.ApiException;
import com.xenon.core.domain.exception.AuthException;
import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.model.ResponseMessage;
import com.xenon.core.domain.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<?> handleApiException(@NonNull ApiException e) {
        return error(ResponseMessage.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler({ClientException.class})
    public ResponseEntity<?> handleClientException(@NonNull ClientException e) {
        return error(ResponseMessage.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({AuthException.class})
    public ResponseEntity<?> handleAuthException(@NonNull AuthException e) {
        return error(e.getResponseMessage() != null ? e.getResponseMessage() : ResponseMessage.AUTHENTICATION_FAILED, e.getMessage());
    }

    protected <T> ResponseEntity<BaseResponse<T>> error(@NonNull ResponseMessage response, @NonNull String message) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        response.getCode(),
                        message,
                        null
                ),
                response.getStatus()
        );
    }
}
