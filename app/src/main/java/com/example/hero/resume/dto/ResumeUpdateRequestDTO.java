package com.example.hero.resume.dto;

import java.util.List;

import okhttp3.MultipartBody;

public class ResumeUpdateRequestDTO {
    private String userIntro;
    private MultipartBody.Part uploadImgFile; //이미지 파일
    private List<String> etcCareer; //외부 경력


}
