package com.cdtn.kltn.dto.base;

import lombok.Data;

@Data
public class BaseResponseData {

    private int status;
    private String message;
    private Object data;

    public BaseResponseData() {
    }

    public BaseResponseData(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
