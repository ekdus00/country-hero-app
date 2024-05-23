package com.example.hero.home.dto;

public class EmployInfoHomeDTO {
    private Integer jobId;
    private String jobName;
    private Long participateCount;

    public EmployInfoHomeDTO(Integer jobId, String jobName, Long participateCount) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.participateCount = participateCount;
    }

    public Long getParticipateCount() {
        return participateCount;
    }

    public void setParticipateCount(Long participateCount) {
        this.participateCount = participateCount;
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
