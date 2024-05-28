package com.example.hero.mypage.dto;

public class OwnerUserInfoResponseDTO {
    private String userName; // 구인자 이름
    private String gender; // 성별
    private String farmName; // 작업장 이름
    private String birth; // 출생연도
    private String mail; // 이메일 주소
    private String loginInfo; // 소셜 로그인(DB를 수정해야 해서 추가할지 여부 확인)

    public OwnerUserInfoResponseDTO(String userName, String gender, String farmName, String birth, String mail, String oAuthInfo){
        this.userName=userName;
        this.gender=gender;
        this.farmName=farmName;
        this.birth=birth;
        this.mail=mail;
        this.loginInfo=oAuthInfo;
    }

    public String getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(String loginInfo) {
        this.loginInfo = loginInfo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
