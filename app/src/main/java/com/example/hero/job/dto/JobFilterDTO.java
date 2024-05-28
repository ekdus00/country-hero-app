package com.example.hero.job.dto;

import java.time.LocalDate;
import java.util.List;

public class JobFilterDTO {
    private List<String> area;
    private List<String> crop;
    private String startWorkDate;
    private String endWorkDate;
    private Integer payGoe;
    private Integer payLoe;
    private String keyWord;
    private String sortType;
    private Double userLatitude;
    private Double userLongitude;

    public JobFilterDTO(List<String> area, List<String> crop, String startWorkDate,
                        String endWorkDate, Integer payGoe, Integer payLoe, String keyWord, String sortType,
                        Double userLatitude, Double userLongitude) {
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

    public String getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(String startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public String getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(String endWorkDate) {
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

