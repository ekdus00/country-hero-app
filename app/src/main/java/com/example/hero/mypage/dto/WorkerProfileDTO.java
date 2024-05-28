package com.example.hero.mypage.dto;

public class WorkerProfileDTO {
    private String userName;
    private Integer userLevel;
    private Integer nextLevel;
    private Integer requiredReviewCount;
    private Double totalReviewAvg;

    public Double getTotalReviewAvg() {
        return totalReviewAvg;
    }

    public void setTotalReviewAvg(Double totalReviewAvg) {
        this.totalReviewAvg = totalReviewAvg;
    }

    public Integer getRequiredReviewCount() {
        return requiredReviewCount;
    }

    public void setRequiredReviewCount(Integer requiredReviewCount) {
        this.requiredReviewCount = requiredReviewCount;
    }

    public Integer getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(Integer nextLevel) {
        this.nextLevel = nextLevel;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
