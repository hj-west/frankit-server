package com.frankit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BaseResponse<T> {

    protected BaseResponseStatus status;
    protected T data;
    @Setter
    protected String message;

    public BaseResponse() {
        this.status = BaseResponseStatus.FRAN200000;
        this.message = this.status.getMessage();
    }

    public BaseResponse(T data) {
        this.status = BaseResponseStatus.FRAN200000;
        this.data = data;
        this.message = this.status.getMessage();
    }

    public BaseResponse(BaseResponseStatus status) {
        this.status = status;
        this.data = null;
        this.message = this.status.getMessage();
    }

    public BaseResponse(BaseResponseStatus status, T data) {
        this.status = status;
        this.data = data;
        this.message = this.status.getMessage();
    }}
