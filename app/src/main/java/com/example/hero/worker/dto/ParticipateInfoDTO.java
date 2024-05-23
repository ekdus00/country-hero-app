package com.example.hero.worker.dto;

import java.time.LocalDate;

public class ParticipateInfoDTO {
    private Integer jobId;
    private String jobName;
    private String country;
    private String city;
    private String cropForm;
    private String cropType;
    private LocalDate startWorkDate;
    private LocalDate endWorkDate;
    private LocalDate startRecruitDate; //모집 시작일
    private LocalDate endRecruitDate; //모집 마감일
    private Integer pay;
    private String approval; //승인여부

    public ParticipateInfoDTO(Integer jobId, String jobName, LocalDate startWorkDate, LocalDate endWorkDate, LocalDate startRecruitDate, LocalDate endRecruitDate, Integer pay, String country, String city, String cropForm, String cropType, String approval) {
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
        this.approval = approval;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public LocalDate getEndRecruitDate() {
        return endRecruitDate;
    }

    public void setEndRecruitDate(LocalDate endRecruitDate) {
        this.endRecruitDate = endRecruitDate;
    }

    public LocalDate getStartRecruitDate() {
        return startRecruitDate;
    }

    public void setStartRecruitDate(LocalDate startRecruitDate) {
        this.startRecruitDate = startRecruitDate;
    }

    public LocalDate getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(LocalDate endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public LocalDate getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(LocalDate startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getCropForm() {
        return cropForm;
    }

    public void setCropForm(String cropForm) {
        this.cropForm = cropForm;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
}
