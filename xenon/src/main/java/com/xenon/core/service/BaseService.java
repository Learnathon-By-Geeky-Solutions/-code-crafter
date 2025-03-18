package com.xenon.core.service;

import com.xenon.core.domain.exception.ClientException;
import com.xenon.core.domain.model.ResponseMessage;
import com.xenon.core.domain.response.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public abstract class BaseService {

    protected <T> void validateBody(T body) {
        if (body == null) throw clientException("Body is required");
    }

    protected ClientException clientException(String message) {
        return new ClientException(message);
    }

    protected ClientException requiredField(String fieldName) {
        return new ClientException(fieldName + " is required");
    }

    protected boolean isNullOrBlank(String field) {
        return field == null || field.isBlank();
    }

    protected <T> ResponseEntity<BaseResponse<T>> success(String message, T data) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        ResponseMessage.OPERATION_SUCCESSFUL.getCode(),
                        Objects.isNull(message) ? ResponseMessage.OPERATION_SUCCESSFUL.getMessage() : message,
                        data
                ),
                ResponseMessage.OPERATION_SUCCESSFUL.getStatus()
        );
    }
}
