package com.example.hero.employer.dto;

import java.util.List;

public class EmployResponseDTO {
    //진행 중인 공고 리스트
    private List<EmployInfoDTO> openEmployJobPostList;
    //모집 마감 공고 리스트
    private List<EmployInfoDTO> closeEmployJobPostList;

    public List<EmployInfoDTO> getOpenEmployJobPostList() {
        return openEmployJobPostList;
    }

    public void setOpenEmployJobPostList(List<EmployInfoDTO> openEmployJobPostList) {
        this.openEmployJobPostList = openEmployJobPostList;
    }

    public List<EmployInfoDTO> getCloseEmployJobPostList() {
        return closeEmployJobPostList;
    }

    public void setCloseEmployJobPostList(List<EmployInfoDTO> closeEmployJobPostList) {
        this.closeEmployJobPostList = closeEmployJobPostList;
    }
}
