package com.example.hero.job.dto;

import java.time.LocalDate;

public class JobInfoDTO {
    private Integer jobId;
    private String jobName;
    private String country;
    private String city;
    private String cropForm;
    private String cropType;
    private String  startWorkDate;
    private String endWorkDate;
    private String startRecruitDate; //모집 시작일
    private String endRecruitDate; //모집 마감일
    private Integer pay;

    public JobInfoDTO(Integer jobId, String jobName, String country, String city, String cropForm, String cropType,
                      String startWorkDate, String endWorkDate, String startRecruitDate, String endRecruitDate, Integer pay){
        this.jobId = jobId;
        this.jobName = jobName;

        this.country = country;
        this.city = city;
        this.cropForm = cropForm;
        this.cropType = cropType;

        this.startWorkDate = startWorkDate;
        this.endWorkDate = endWorkDate;
        this.startRecruitDate = startRecruitDate;
        this.endRecruitDate = endRecruitDate;

        this.pay = pay;

    }

    public Integer getId() {
        return jobId;
    }

    public void setId(Integer jobId) {
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


    public String getStartRecruitDate() {
        return startRecruitDate;
    }

    public void setStartRecruitDate(String startRecruitDate) {
        this.startRecruitDate = startRecruitDate;
    }


    public String getEndRecruitDate() {
        return endRecruitDate;
    }

    public void setEndRecruitDate(String endRecruitDate) {
        this.endRecruitDate = endRecruitDate;
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



    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }
}
