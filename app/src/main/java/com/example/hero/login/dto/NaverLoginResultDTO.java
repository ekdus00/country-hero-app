package com.example.hero.login.dto;

import com.example.hero.home.dto.OwnerHomeDTO;
import com.example.hero.home.dto.WorkerHomeDTO;

public class NaverLoginResultDTO {
    private OwnerHomeDTO ownerHomeDTO;
    private WorkerHomeDTO workerHomeDTO;
    private JWTPayloadDTO jwtPayloadDTO;

    public NaverLoginResultDTO(OwnerHomeDTO ownerHomeDTO, JWTPayloadDTO jwtPayloadDTO) {
        this.ownerHomeDTO = ownerHomeDTO;
        this.jwtPayloadDTO = jwtPayloadDTO;
    }

    public NaverLoginResultDTO(WorkerHomeDTO workerHomeDTO, JWTPayloadDTO jwtPayloadDTO) {
        this.workerHomeDTO = workerHomeDTO;
        this.jwtPayloadDTO = jwtPayloadDTO;
    }

    public JWTPayloadDTO getJwtPayloadDTO() {
        return jwtPayloadDTO;
    }

    public void setJwtPayloadDTO(JWTPayloadDTO jwtPayloadDTO) {
        this.jwtPayloadDTO = jwtPayloadDTO;
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
