package com.xenon.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    AUTHENTICATION_FAILED("XE0001", "Authentication is required to access this resource", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("XE0002", "You don't have permission to access this resource", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("XE0003", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("XE0004", "Bad Request", HttpStatus.BAD_REQUEST),
    OPERATION_SUCCESSFUL("XS0001", "This operation has been completed successfully", HttpStatus.OK);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
