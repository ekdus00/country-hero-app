package com.example.hero.employer.dto;

import java.time.LocalDate;

public class EmployInfoDTO {
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
    private String state;
    private Long participateCount;

    public EmployInfoDTO(Integer jobId, String jobName, LocalDate startWorkDate, LocalDate endWorkDate, LocalDate startRecruitDate, LocalDate endRecruitDate, String country, String city, String cropForm, String cropType, String state, Long participateCount) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.startWorkDate = startWorkDate;
        this.endWorkDate = endWorkDate;
        this.startRecruitDate = startRecruitDate;
        this.endRecruitDate = endRecruitDate;
        this.country = country;
        this.city = city;
        this.cropForm = cropForm;
        this.cropType = cropType;
        this.state = state;
        this.participateCount = participateCount;
    }

    public Long getParticipateCount() {
        return participateCount;
    }

    public void setParticipateCount(Long participateCount) {
        this.participateCount = participateCount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
