package com.example.hero.resume.dto;

import java.util.List;

public class ResumeEditResponseDTO {
    private String userName;
    private String birth;
    private String gender;
    private String userIntro;
    private List<String> etcCareer;
    private Double totalReviewScore;
    private String uploadImgFileName;

    public String getUploadImgFileName() {
        return uploadImgFileName;
    }

    public void setUploadImgFileName(String uploadImgFileName) {
        this.uploadImgFileName = uploadImgFileName;
    }

    public Double getTotalReviewScore() {
        return totalReviewScore;
    }

    public void setTotalReviewScore(Double totalReviewScore) {
        this.totalReviewScore = totalReviewScore;
    }

    public List<String> getEtcCareer() {
        return etcCareer;
    }

    public void setEtcCareer(List<String> etcCareer) {
        this.etcCareer = etcCareer;
    }

    public String getUserIntro() {
        return userIntro;
    }

    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
