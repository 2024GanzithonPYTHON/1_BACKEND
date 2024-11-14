package com.ganzithon.go_farming.common.exception;

import lombok.Data;

@Data
public class ExceptionDTO {

    private int code;
    private String msg;

    public ExceptionDTO(CustomException e) {
        this.code = e.getCode();
        this.msg = e.getMessage();
    }
}
