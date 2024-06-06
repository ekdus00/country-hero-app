package com.example.hero.login.dto;

import com.example.hero.home.dto.OwnerHomeDTO;
import com.example.hero.home.dto.WorkerHomeDTO;

public class NaverLoginResultDTO {
    private OwnerHomeDTO ownerHomeDTO;
    private WorkerHomeDTO workerHomeDTO;
    private String userType; // 사용자 유형

    public NaverLoginResultDTO(OwnerHomeDTO ownerHomeDTO, String userType) {
        this.ownerHomeDTO = ownerHomeDTO;
        this.userType = userType;
    }

    public NaverLoginResultDTO(WorkerHomeDTO workerHomeDTO, String userType) {
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
