package com.example.hero.matching.dto;

public class MatchingDetailResponseDTO {

    private String userId; // 매칭 글 작성자 id
    private String userName; //매칭 글 작성자
    private String matchingName; //매칭글 명
    private String country; //시/도
    private String city; //시/군/구
    private String startEduDate; //교육 시작일
    private String endEduDate; //교육 마감일
    private String eduContent; // 교육 내용
    private String snsUrl;  // sns 링크
    private Integer viewCount; //조회수
    private String matchingImgFile;

    public MatchingDetailResponseDTO(String userId, String userName, String matchingName, String country, String city, String startEduDate, String endEduDate, String eduContent, String snsUrl, Integer viewCount, String matchingImgFile) {
        this.userId = userId;
        this.userName = userName;
        this.matchingName = matchingName;
        this.country = country;
        this.city = city;
        this.startEduDate = startEduDate;
        this.endEduDate = endEduDate;
        this.eduContent = eduContent;
        this.snsUrl = snsUrl;
        this.viewCount = viewCount;
        this.matchingImgFile = matchingImgFile;
    }

    public boolean isMyPost(String userId) {
        return this.userId.equals(userId);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMatchingName() {
        return matchingName;
    }

    public void setMatchingName(String matchingName) {
        this.matchingName = matchingName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStartEduDate() {
        return startEduDate;
    }

    public void setStartEduDate(String startEduDate) {
        this.startEduDate = startEduDate;
    }

    public String getEndEduDate() {
        return endEduDate;
    }

    public void setEndEduDate(String endEduDate) {
        this.endEduDate = endEduDate;
    }

    public String getEduContent() {
        return eduContent;
    }

    public void setEduContent(String eduContent) {
        this.eduContent = eduContent;
    }

    public String getSnsUrl() {
        return snsUrl;
    }

    public void setSnsUrl(String snsUrl) {
        this.snsUrl = snsUrl;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getMatchingImgFile() {
        return matchingImgFile;
    }

    public void setMatchingImgFile(String matchingImgFile) {
        this.matchingImgFile = matchingImgFile;
    }
}