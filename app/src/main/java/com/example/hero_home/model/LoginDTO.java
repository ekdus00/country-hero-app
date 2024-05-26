package com.example.hero_home.model;

// 로그인 기능을 위한 Model
// 로그인 정보(아이디, 비밀번호)를 body에 실어서 보내기 위한 Model

public class LoginDTO {
    private String userId;
    private String userPw;

    public LoginDTO(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }
}
