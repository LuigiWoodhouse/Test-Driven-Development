package org.example.exception;

import lombok.Data;

@Data
public class ItemNotFoundException extends Exception {


    private static final long serialVersionUID = 1L;

    private int code;

    public ItemNotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }
}
