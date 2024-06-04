package com.example.hero.login.dto;

import com.example.hero.home.dto.OwnerHomeDTO;
import com.example.hero.home.dto.WorkerHomeDTO;

public class LoginResultDTO {
    private OwnerHomeDTO ownerHomeDTO; // 구인자 홈 필요 데이터
    private WorkerHomeDTO workerHomeDTO; // 구직자 홈 필요 데이터
    private String userType; // 사용자 유형

    public LoginResultDTO(OwnerHomeDTO ownerHomeDTO, String userType) {
        this.ownerHomeDTO = ownerHomeDTO;
        this.userType = userType;
    }

    public LoginResultDTO(WorkerHomeDTO workerHomeDTO, String userType) {
        this.workerHomeDTO = workerHomeDTO;
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public WorkerHomeDTO getWorkerHomeDTO() {
        return workerHomeDTO;
    }

    public void setWorkerHomeDTO(WorkerHomeDTO workerHomeDTO) {
        this.workerHomeDTO = workerHomeDTO;
    }

    public OwnerHomeDTO getOwnerHomeDTO() {
        return ownerHomeDTO;
    }

    public void setOwnerHomeDTO(OwnerHomeDTO ownerHomeDTO) {
        this.ownerHomeDTO = ownerHomeDTO;
    }
}
