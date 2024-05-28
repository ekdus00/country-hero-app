package com.example.hero.matching.dto;

public class MatchingPostCreateRequestDTO {
    private String userId; //매칭글 작성자
    private String userType; //사용자 유형(구인자/구직자)
    private String country; //시/도
    private String city; //시/군/구
    private String matchingName; //매칭글 명
    private String writerType;  // 매칭글 작성자 유형(멘토/멘티)
    private String startEduDate; //작업 시작일
    private String endEduDate; //작업 마감일
    private String eduContent; //작업 요일
    private String snsUrl; //소개

    public MatchingPostCreateRequestDTO(String userId, String userType, String country, String city, String matchingName, String writerType, String startEduDate, String endEduDate, String eduContent, String snsUrl) {
        this.userId = userId;
        this.userType = userType;
        this.country = country;
        this.city = city;
        this.matchingName = matchingName;
        this.writerType = writerType;
        this.startEduDate = startEduDate;
        this.endEduDate = endEduDate;
        this.eduContent = eduContent;
        this.snsUrl = snsUrl;
    }
}
