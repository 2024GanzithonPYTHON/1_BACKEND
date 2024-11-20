package com.ganzithon.go_farming.common.response;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private String msg;
    private int code;
    private T data;

    public ResponseDTO(T data, Responses responses) {
        this.msg = responses.getMsg();
        this.code = responses.getCode();
        this.data = data;
    }

    public ResponseDTO(Responses responses) {
        this.msg = responses.getMsg();
        this.code = responses.getCode();
    }
}
