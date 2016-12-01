package com.aug.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;



public class ResponseWrapper {
    private int code;
    private String message;
    private Object data;

    public static ResponseWrapper NO_CONTENT = new ResponseWrapper(HttpStatus.NO_CONTENT);
    public static ResponseWrapper OK = new ResponseWrapper(HttpStatus.OK);

    public ResponseWrapper() {
        this.code = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
    }

    public ResponseWrapper(HttpStatus status) {
        this.code = status.value();
        this.message = status.getReasonPhrase();
    }

    @SuppressWarnings("rawtypes")
    public ResponseWrapper(Object data) {
        if (data == null || data instanceof Collection && ((Collection) data).isEmpty()) {
            this.code = HttpStatus.NO_CONTENT.value();
            this.message = HttpStatus.NO_CONTENT.getReasonPhrase();
        } else {
            this.code = HttpStatus.OK.value();
            this.message = HttpStatus.OK.getReasonPhrase();
            this.data = data;
        }
    }

    public ResponseWrapper(HttpStatus status, Object data) {
        this.code = status.value();
        this.message = status.getReasonPhrase();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
