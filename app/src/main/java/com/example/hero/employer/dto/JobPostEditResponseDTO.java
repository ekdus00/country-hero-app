package com.example.hero.employer.dto;

import java.time.LocalDate;
import java.time.LocalTime;

//수정 응답
public class JobPostEditResponseDTO {
    private String jobName; //공고 명
    private String country; //시/도
    private String city; //시/군/구
    private String startWorkDate; //작업 시작일
    private String endWorkDate; //작업 마감일
    private String startWorkTime; //작업 시작시간
    private String endWorkTime; //작업 마감 시간
    private String workDay; //작업 요일(다른 데이터 타입으로 저장해야 할 듯)
    private Integer pay; //급여
    private String startRecruitDate; //모집 시작일
    private String endRecruitDate; //모집 마감일
    private Integer recruitCount; //모집 인원
    private String cropForm; //작물 형태
    private String cropType; //농작물 품목
    private Integer age; //나이
    private String career; //경력 유무
    private String spec; //우대조건
    private String jobIntro; //소개
    private String workAddress; //작업장 주소 코드
    private String workAddressDetail; //작업장 상세 주소
    private String uploadImgFileName; //이미지 파일 이름

    public String getUploadImgFileName() {
        return uploadImgFileName;
    }

    public void setUploadImgFileName(String uploadImgFileName) {
        this.uploadImgFileName = uploadImgFileName;
    }

    public String getWorkAddressDetail() {
        return workAddressDetail;
    }

    public void setWorkAddressDetail(String workAddressDetail) {
        this.workAddressDetail = workAddressDetail;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getJobIntro() {
        return jobIntro;
    }

    public void setJobIntro(String jobIntro) {
        this.jobIntro = jobIntro;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getCropForm() {
        return cropForm;
    }

    public void setCropForm(String cropForm) {
        this.cropForm = cropForm;
    }

    public Integer getRecruitCount() {
        return recruitCount;
    }

    public void setRecruitCount(Integer recruitCount) {
        this.recruitCount = recruitCount;
    }

    public String getEndRecruitDate() {
        return endRecruitDate;
    }

    public void setEndRecruitDate(String endRecruitDate) {
        this.endRecruitDate = endRecruitDate;
    }

    public String getStartRecruitDate() {
        return startRecruitDate;
    }

    public void setStartRecruitDate(String startRecruitDate) {
        this.startRecruitDate = startRecruitDate;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public String getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public String getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(String endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public String getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(String startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
