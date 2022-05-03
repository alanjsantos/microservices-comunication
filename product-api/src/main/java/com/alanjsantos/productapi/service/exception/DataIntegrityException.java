package com.alanjsantos.productapi.service.exception;

public class DataIntegrityException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public DataIntegrityException(String msg) {
        super(msg);
    }
    public DataIntegrityException(String msgs, Throwable cause) {
        super(msgs, cause);
    }

}
