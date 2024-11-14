package com.ganzithon.go_farming.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Exceptions {

    /*400 BAD_REQUEST*/
    BAD_REQUEST(400, "Bad Request"),

    /*401 UNAUTHORIZED*/
    UNAUTHORIZED(401, "Unauthorized"),

    /*403 FORBIDDEN*/
    FORBIDDEN(403, "Forbidden"),

    /*404 NOT_FOUND*/
    NOT_FOUND(404, "Not Found"),
    MEMBER_NOT_EXIST(404, "Member Not Exist"),

    /*405 METHOD_NOT_ALLOWED*/
    METHOD_NOT_ALLOWED(405, "Method Not Allowed");

    private final int code;
    private final String msg;
}
