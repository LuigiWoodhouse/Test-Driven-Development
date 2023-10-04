package org.example.exception;

import lombok.Data;

@Data
public class InvalidCardException extends Exception{

    private static final long serialVersionUID = 1L;

    private int code;

    public InvalidCardException(int code, String message) {
        super(message);
        this.code = code;
    }
}