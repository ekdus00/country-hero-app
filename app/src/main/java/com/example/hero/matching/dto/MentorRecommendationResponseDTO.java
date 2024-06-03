package com.example.hero.matching.dto;

public class MentorRecommendationResponseDTO {
    private String userId; //멘토의 사용자 ID
    private String userType; //멘토의 사용자 유형(구인자/구직자)
    private String userName; //멘토 이름

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
