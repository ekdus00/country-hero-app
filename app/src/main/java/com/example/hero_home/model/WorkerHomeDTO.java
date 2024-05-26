package com.example.hero_home.model;

import java.util.List;

// 로그인 기능을 위한 Model
// 로그인 성공 시 받아오는 Response를 위해 만든 클래스
public class WorkerHomeDTO {
    private List<JobInfoHomeDTO> jobList;
    private List<ParticipateInfoHomeDTO> participateList;

    public WorkerHomeDTO(List<JobInfoHomeDTO> jobList, List<ParticipateInfoHomeDTO> participateList) {
        this.jobList = jobList;
        this.participateList = participateList;
    }
}
