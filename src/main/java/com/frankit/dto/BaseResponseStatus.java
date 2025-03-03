package com.frankit.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseStatus {
    FRAN200000(HttpStatus.OK),
    FRAN400000(HttpStatus.BAD_REQUEST),
    FRAN401000(HttpStatus.UNAUTHORIZED),
    FRAN401001(HttpStatus.UNAUTHORIZED, "Invalid email or password"),
    FRAN402000(HttpStatus.PAYMENT_REQUIRED),
    FRAN403000(HttpStatus.FORBIDDEN),
    FRAN404000(HttpStatus.NOT_FOUND),
    FRAN404001(HttpStatus.NOT_FOUND, "user not found"),
    FRAN405000(HttpStatus.METHOD_NOT_ALLOWED),
    FRAN500000(HttpStatus.INTERNAL_SERVER_ERROR),
    FRAN501000(HttpStatus.NOT_IMPLEMENTED),
    FRAN502000(HttpStatus.BAD_GATEWAY),
    FRAN503000(HttpStatus.SERVICE_UNAVAILABLE),
    FRAN504000(HttpStatus.GATEWAY_TIMEOUT),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    BaseResponseStatus(HttpStatus httpStatus) {
        this(httpStatus, httpStatus.getReasonPhrase());
    }

    BaseResponseStatus(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
