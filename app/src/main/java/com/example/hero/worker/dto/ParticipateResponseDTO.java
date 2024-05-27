package com.example.hero.worker.dto;

import java.util.List;

public class ParticipateResponseDTO {
    //지원 현황 리스트(승인, 보류, 작업 완료 이전)
    private List<ParticipateInfoDTO> jobRequestList;
    //승인된 일자리 공고 리스트
    private List<ParticipateInfoDTO> approveParticipateList;
    //보류된 일자리 공고 리스트
    private List<ParticipateInfoDTO> deferParticipateList;
    //이전 활동 내역 리스트
    private List<CareerDTO> previousParticipateList;

    public List<ParticipateInfoDTO> getJobRequestList() {
        return jobRequestList;
    }

    public void setJobRequestList(List<ParticipateInfoDTO> jobRequestList) {
        this.jobRequestList = jobRequestList;
    }

    public List<ParticipateInfoDTO> getApproveParticipateList() {
        return approveParticipateList;
    }

    public void setApproveParticipateList(List<ParticipateInfoDTO> approveParticipateList) {
        this.approveParticipateList = approveParticipateList;
    }

    public List<ParticipateInfoDTO> getDeferParticipateList() {
        return deferParticipateList;
    }

    public void setDeferParticipateList(List<ParticipateInfoDTO> deferParticipateList) {
        this.deferParticipateList = deferParticipateList;
    }

    public List<CareerDTO> getPreviousParticipateList() {
        return previousParticipateList;
    }

    public void setPreviousParticipateList(List<CareerDTO> previousParticipateList) {
        this.previousParticipateList = previousParticipateList;
    }
}
