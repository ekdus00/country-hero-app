package com.example.hero;

import java.time.LocalDate;

public class ClipDTO {
    private Integer jobId;
    private String jobName;
    private String country;
    private String city;
    private String cropForm;
    private String cropType;
    private LocalDate startWorkDate;
    private LocalDate endWorkDate;
    private LocalDate startRecruitDate;
    private LocalDate endRecruitDate;
    private Integer pay;

    public ClipDTO(Integer jobId, String jobName, LocalDate startWorkDate, LocalDate endWorkDate, LocalDate startRecruitDate, LocalDate endRecruitDate, Integer pay, String country, String city, String cropForm, String cropType) {
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

    public String getJobName() {
        return jobName;
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

    public String getPay() {
        return String.valueOf(pay);
    }
}
