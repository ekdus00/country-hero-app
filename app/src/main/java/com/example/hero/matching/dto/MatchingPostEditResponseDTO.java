package com.example.hero.matching.dto;

import java.time.LocalDate;

public class MatchingPostEditResponseDTO {
    private String matchingName; //매칭글 명
    private String country; //시/도
    private String city; //시/군/구
    private String startEduDate; //교육 시작일
    private String endEduDate; //교육 마감일
    private String eduContent; // 교육 내용
    private String snsUrl;  // sns 링크
    private String uploadImgFileName; //이미지 파일 이름

    public MatchingPostEditResponseDTO(String matchingName, String country, String city, String startEduDate, String endEduDate, String eduContent, String snsUrl, String uploadImgFileName) {
        this.matchingName = matchingName;
        this.country = country;
        this.city = city;
        this.startEduDate = startEduDate;
        this.endEduDate = endEduDate;
        this.eduContent = eduContent;
        this.snsUrl = snsUrl;
        this.uploadImgFileName = uploadImgFileName;
    }

    public String getMatchingName() {
        return matchingName;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStartEduDate() {
        return startEduDate;
    }

    public String getEndEduDate() {
        return endEduDate;
    }

    public String getEduContent() {
        return eduContent;
    }

    public String getSnsUrl() {
        return snsUrl;
    }

    public String getUploadImgFileName() {
        return uploadImgFileName;
    }
}