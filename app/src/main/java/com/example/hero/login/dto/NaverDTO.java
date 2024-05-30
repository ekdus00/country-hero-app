package com.example.hero.login.dto;

public class NaverDTO {
    //사용자 아이디
    private String id;
    //사용자 이름
    private String name;
    //사용자 이메일
    private String email;
    //사용자 성별
    private String gender;
    //사용자 생년
    private String birth;
    //사용자 유형
    private String userType;
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
