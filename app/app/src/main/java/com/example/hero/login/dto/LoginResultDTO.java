package com.example.hero.login.dto;

import com.example.hero.home.dto.OwnerHomeDTO;
import com.example.hero.home.dto.WorkerHomeDTO;

public class LoginResultDTO {
    private OwnerHomeDTO ownerHomeDTO;
    private WorkerHomeDTO workerHomeDTO;
    private JWTPayloadDTO jwtPayloadDTO;

    public LoginResultDTO(OwnerHomeDTO ownerHomeDTO, JWTPayloadDTO jwtPayloadDTO) {
        this.ownerHomeDTO = ownerHomeDTO;
        this.jwtPayloadDTO = jwtPayloadDTO;
    }

    public LoginResultDTO(WorkerHomeDTO workerHomeDTO, JWTPayloadDTO jwtPayloadDTO) {
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
