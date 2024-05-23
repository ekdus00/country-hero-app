package com.example.hero.job.dto;

public class OwnerInfoResponseDTO {
    private OwnerInfoDTO ownerInfoDTO;
    private OwnerReviewResultDTO ownerReviewResultDTO;

    public OwnerReviewResultDTO getOwnerReviewResultDTO() {
        return ownerReviewResultDTO;
    }

    public void setOwnerReviewResultDTO(OwnerReviewResultDTO ownerReviewResultDTO) {
        this.ownerReviewResultDTO = ownerReviewResultDTO;
    }

    public OwnerInfoDTO getOwnerInfoDTO() {
        return ownerInfoDTO;
    }

    public void setOwnerInfoDTO(OwnerInfoDTO ownerInfoDTO) {
        this.ownerInfoDTO = ownerInfoDTO;
    }
}
