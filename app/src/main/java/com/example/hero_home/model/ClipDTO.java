package com.example.hero_home.model;

import java.time.LocalDate;

public class ClipDTO {
    private Integer jobId;
    private String jobName;
    private String country;
    private String city;
    private String cropForm;
    private String cropType;
    private String startWorkDate;
    private String endWorkDate;
    private String startRecruitDate;
    private String endRecruitDate;
    private Integer pay;

    public ClipDTO(Integer jobId, String jobName, String startWorkDate, String endWorkDate, String startRecruitDate, String endRecruitDate, Integer pay, String country, String city, String cropForm, String cropType) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.startWorkDate = startWorkDate;
        this.endWorkDate = endWorkDate;
        this.startRecruitDate = startRecruitDate;
        this.endRecruitDate = endRecruitDate;
        this.pay = pay;
        this.country = country;
        this.city = city;
        this.cropForm = cropForm;
        this.cropType = cropType;
    }

    public Integer getJobId() {
        return jobId;
    }
    public String getJobName() {
        return jobName;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCropForm(String cropForm) {
        this.cropForm = cropForm;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public void setStartWorkDate(String startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public void setEndWorkDate(String endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public void setStartRecruitDate(String startRecruitDate) {
        this.startRecruitDate = startRecruitDate;
    }

    public void setEndRecruitDate(String endRecruitDate) {
        this.endRecruitDate = endRecruitDate;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public String getCountry() {
        return country;
    }

    public String getStartWorkDate() {
        return startWorkDate.toString();
    }

    public String getEndWorkDate() {
        return endWorkDate.toString();
    }

    public Integer getPay() {
        return pay;
    }

    public String getCropType() {
        return cropType;
    }

    public String getCropForm() {
        return cropForm;
    }

    public String getCity() {
        return city;
    }

    public String getStartRecruitDate() {
        return startRecruitDate;
    }

    public String getEndRecruitDate() {
        return endRecruitDate;
    }
}
