package com.example.hero.resume.dto;

import java.util.List;

import okhttp3.MultipartBody;

public class ResumeUpdateRequestDTO {
    private String userIntro;
    private MultipartBody.Part uploadImgFile; //이미지 파일
    private List<String> etcCareer; //외부 경력

    public List<String> getEtcCareer() {
        return etcCareer;
    }

    public void setEtcCareer(List<String> etcCareer) {
        this.etcCareer = etcCareer;
    }

    public MultipartBody.Part getUploadImgFile() {
        return uploadImgFile;
    }

    public void setUploadImgFile(MultipartBody.Part uploadImgFile) {
        this.uploadImgFile = uploadImgFile;
    }

    public String getUserIntro() {
        return userIntro;
    }

    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro;
    }
}
