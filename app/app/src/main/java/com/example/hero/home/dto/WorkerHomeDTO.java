package com.example.hero.home.dto;

import java.util.List;

public class WorkerHomeDTO {
    private List<JobInfoHomeDTO> jobList;
    private List<ParticipateInfoHomeDTO> participateList;

    public List<JobInfoHomeDTO> getJobList() {
        return jobList;
    }

    public void setJobList(List<JobInfoHomeDTO> jobList) {
        this.jobList = jobList;
    }

    public List<ParticipateInfoHomeDTO> getParticipateList() {
        return participateList;
    }

    public void setParticipateList(List<ParticipateInfoHomeDTO> participateList) {
        this.participateList = participateList;
    }
}
