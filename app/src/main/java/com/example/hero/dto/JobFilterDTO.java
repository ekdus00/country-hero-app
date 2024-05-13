package com.example.hero.dto;

import java.time.LocalDate;
import java.util.List;

public class JobFilterDTO {
    private String userId;
    private String userType;
    private List<String> area;
    private List<String> crop;
    private LocalDate startWorkDate;
    private LocalDate endWorkDate;
    private Integer payGoe;
    private Integer payLoe;
    private String keyWord;
    private String sortType;
    private Double userLatitude;
    private Double userLongitude;

    public JobFilterDTO(String userId, String userType, List<String> area, List<String> crop, LocalDate startWorkDate,
                        LocalDate endWorkDate, Integer payGoe, Integer payLoe, String keyWord, String sortType,
                        Double userLatitude, Double userLongitude) {
        this.userId = userId;
        this.userType = userType;
        this.area = area;
        this.crop = crop;
        this.startWorkDate = startWorkDate;
        this.endWorkDate = endWorkDate;
        this.payGoe = payGoe;
        this.payLoe = payLoe;
        this.keyWord = keyWord;
        this.sortType = sortType;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;

    }
    public JobFilterDTO() {
        // 기본 생성자
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }

    public List<String> getCrop() {
        return crop;
    }

    public void setCrop(List<String> crop) {
        this.crop = crop;
    }

    public LocalDate getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(LocalDate startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public LocalDate getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(LocalDate endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public Integer getPayGoe() {
        return payGoe;
    }

    public void setPayGoe(Integer payGoe) {
        this.payGoe = payGoe;
    }

    public Integer getPayLoe() {
        return payLoe;
    }

    public void setPayLoe(Integer payLoe) {
        this.payLoe = payLoe;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(Double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public Double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(Double userLongitude) {
        this.userLongitude = userLongitude;
    }
}

