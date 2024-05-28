package com.example.hero.mypage.dto;

public class OwnerProfileDTO {
    private String userName;
    private Double totalReviewAvg;

    public Double getTotalReviewAvg() {
        return totalReviewAvg;
    }

    public void setTotalReviewAvg(Double totalReviewAvg) {
        this.totalReviewAvg = totalReviewAvg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
