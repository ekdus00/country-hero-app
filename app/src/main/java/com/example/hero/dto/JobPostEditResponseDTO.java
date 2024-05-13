package com.example.hero.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class JobPostEditResponseDTO {
    private String userName; //공고 작성자
    private String jobName; //공고 명
    private String country; //시/도
    private String city; //시/군/구
    private LocalDate startWorkDate; //작업 시작일
    private LocalDate endWorkDate; //작업 마감일
    private LocalTime startWorkTime; //작업 시작시간
    private LocalTime endWorkTime; //작업 마감 시간
    private String workDay; //작업 요일(다른 데이터 타입으로 저장해야 할 듯)
    private Integer pay; //급여
    private LocalDate startRecruitDate; //모집 시작일
    private LocalDate endRecruitDate; //모집 마감일
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


    public JobPostEditResponseDTO(String userName, String jobName, String country, String city,
                                   LocalDate startWorkDate, LocalDate endWorkDate, LocalTime startWorkTime, LocalTime endWorkTime,
                                   String workDay, Integer pay, LocalDate startRecruitDate, LocalDate endRecruitDate,
                                   Integer recruitCount, String cropForm, String cropType, Integer age, String career,
                                   String spec, String jobIntro, String workAddress, String uploadImgFileName) {
        this.userName = userName;
        this.jobName = jobName;
        this.country = country;
        this.city = city;
        this.startWorkDate = startWorkDate;
        this.endWorkDate = endWorkDate;
        this.startWorkTime = startWorkTime;
        this.endWorkTime = endWorkTime;
        this.workDay = workDay;
        this.pay = pay;
        this.startRecruitDate = startRecruitDate;
        this.endRecruitDate = endRecruitDate;
        this.recruitCount = recruitCount;
        this.cropForm = cropForm;
        this.cropType = cropType;
        this.age = age;
        this.career = career;
        this.spec = spec;
        this.jobIntro = jobIntro;
        this.workAddress = workAddress;
        this.uploadImgFileName = uploadImgFileName;
    }
    public JobPostEditResponseDTO() {
        // 기본 생성자
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(LocalDate startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public LocalDate getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(LocalDate endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public LocalTime getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(LocalTime startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public LocalTime getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(LocalTime endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public LocalDate getStartRecruitDate() {
        return startRecruitDate;
    }

    public void setStartRecruitDate(LocalDate startRecruitDate) {
        this.startRecruitDate = startRecruitDate;
    }

    public LocalDate getEndRecruitDate() {
        return endRecruitDate;
    }

    public void setEndRecruitDate(LocalDate endRecruitDate) {
        this.endRecruitDate = endRecruitDate;
    }

    public Integer getRecruitCount() {
        return recruitCount;
    }

    public void setRecruitCount(Integer recruitCount) {
        this.recruitCount = recruitCount;
    }

    public String getCropForm() {
        return cropForm;
    }

    public void setCropForm(String cropForm) {
        this.cropForm = cropForm;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getJobIntro() {
        return jobIntro;
    }

    public void setJobIntro(String jobIntro) {
        this.jobIntro = jobIntro;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getWorkAddressDetail() {
        return workAddressDetail;
    }

    public void setWorkAddressDetail(String workAddressDetail) {
        this.workAddressDetail = workAddressDetail;
    }

    public String getUploadImgFileName() {
        return uploadImgFileName;
    }

    public void setUploadImgFileName(String uploadImgFileName) {
        this.uploadImgFileName = uploadImgFileName;
    }

}
