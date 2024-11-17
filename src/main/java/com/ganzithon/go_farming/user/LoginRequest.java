package com.ganzithon.go_farming.user;

import lombok.Data;

@Data // Lombok을 사용해 Getter, Setter, ToString 등을 자동 생성
public class LoginRequest {

    private String username; // 사용자의 아이디
    private String password; // 사용자의 비밀번호
}
