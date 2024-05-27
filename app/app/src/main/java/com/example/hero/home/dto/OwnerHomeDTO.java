package com.example.hero.home.dto;

import java.util.List;

public class OwnerHomeDTO {
    private List<EmployInfoHomeDTO> empolyInfoHomeDTOList;
    private Integer employCount;
    private Double totalReviewAvg;
    private List<JobInfoHomeDTO> jobList;

    public List<EmployInfoHomeDTO> getEmpolyInfoHomeDTOList() {
        return empolyInfoHomeDTOList;
    }

    public void setEmpolyInfoHomeDTOList(List<EmployInfoHomeDTO> empolyInfoHomeDTOList) {
        this.empolyInfoHomeDTOList = empolyInfoHomeDTOList;
    }

    public Integer getEmployCount() {
        return employCount;
    }

    public void setEmployCount(Integer employCount) {
        this.employCount = employCount;
    }

    public Double getTotalReviewAvg() {
        return totalReviewAvg;
    }

    public void setTotalReviewAvg(Double totalReviewAvg) {
        this.totalReviewAvg = totalReviewAvg;
    }

    public List<JobInfoHomeDTO> getJobList() {
        return jobList;
    }

    public void setJobList(List<JobInfoHomeDTO> jobList) {
        this.jobList = jobList;
    }
}
