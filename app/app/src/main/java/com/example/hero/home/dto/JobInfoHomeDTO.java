package com.example.hero.home.dto;

import java.time.LocalDate;

public class JobInfoHomeDTO {
    private Integer jobId; //공고 번호
    private String jobName; //공고 명
    private String startWorkDate; //작업 시작일
    private String endWorkDate; //작업 마감일
    private String country; //시/도
    private String city; //시/군/구
    private String cropForm; //작물 형태
    private String cropType; //농작물 품목

    public JobInfoHomeDTO(Integer jobId, String jobName, String startWorkDate, String endWorkDate, String country, String city, String cropForm, String cropType) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.startWorkDate = startWorkDate;
        this.endWorkDate = endWorkDate;
        this.country = country;
        this.city = city;
        this.cropForm = cropForm;
        this.cropType = cropType;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
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

    public String getCropForm() {
        return cropForm;
    }

    public void setCropForm(String cropForm) {
        this.cropForm = cropForm;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }
}