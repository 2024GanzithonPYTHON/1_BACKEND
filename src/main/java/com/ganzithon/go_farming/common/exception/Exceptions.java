package com.ganzithon.go_farming.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Exceptions {

    /*400 BAD_REQUEST*/
    BAD_REQUEST(400, "Bad Request"),
    PLACE_NOT_FOUND(400, "카카오맵 api에서 해당 장소를 조회할 수 없습니다."),

    /*401 UNAUTHORIZED*/
    UNAUTHORIZED(401, "권한이 없습니다."),

    /*403 FORBIDDEN*/
    FORBIDDEN(403, "Forbidden"),

    /*404 NOT_FOUND*/
    NOT_FOUND(404, "Not Found"),
    MEMBER_NOT_EXIST(404, "Member Not Exist"),
    PLACE_NOT_EXIST(404, "해당 id를 가진 장소가 존재하지 않습니다."),
    REVIEW_NOT_EXIST(404, "해당 id를 가진 리뷰가 존재하지 않습니다."),
    QUESTION_NOT_EXIST(404, "해당 id를 가진 질문이 존재하지 않습니다."),
    KEYWORD_NOT_EXIST(404, "해당 id를 가진 키워드가 존재하지 않습니다."),

    /*405 METHOD_NOT_ALLOWED*/
    METHOD_NOT_ALLOWED(405, "Method Not Allowed");

    private final int code;
    private final String msg;
}
