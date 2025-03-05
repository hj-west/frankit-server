package com.frankit.exception;

public class ProductOptionLimitExceededException extends RuntimeException {
    public ProductOptionLimitExceededException(String message) {
        super(message);
    }
}

