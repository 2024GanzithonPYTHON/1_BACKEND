package com.ganzithon.go_farming.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final Exceptions exceptions;

    public int getCode() { return exceptions.getCode(); }

    public String getMessage() { return exceptions.getMsg(); }

}
