package com.example.hero.job.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class JobDetailResponseDTO {
    private String userName;  // 공고 작성자
    private String jobName;   // 공고 명
    private String createdJobDate; // 공고 작성일
    private String startWorkDate;  // 작업 시작일
    private String endWorkDate;    // 작업 종료일
    private String startWorkTime; // 작업 시작 시간
    private String endWorkTime;   // 작업 종료 시간
    private String workDay;              // 작업 요일
    private Integer pay;                 // 급여
    private Integer recruitCount;        // 모집 인원
    private String startRecruitDate;  // 모집 시작일
    private String endRecruitDate;    // 모집 종료일
    private Integer age;                  // 나이
    private String career;               // 경력
    private String spec;                 // 우대사항
    private String jobIntro;             // 작업 소개
    private String workAddress;          // 작업장 주소
    private String workAddressDetail;    // 작업장 상세 주소
    private Integer viewCount;           // 조회수
    private String country;              // 시/도
    private String city;                 // 시/군/구
    private String cropType;
    private String cropForm;
    private Double workLatitude; //사용자 위도
    private Double workLongitude; //사용자 경도
    private String jobImgFile;
    private Integer cropLevel;


    public JobDetailResponseDTO(String userName, String jobName, String createdJobDate, String country, String city,
                                String startWorkDate, String endWorkDate, String startWorkTime, String endWorkTime,
                                String workDay, Integer pay, String startRecruitDate, String endRecruitDate,
                                Integer recruitCount, String cropForm, String cropType, Integer age, String career,
                                String spec, String jobIntro, String workAddress, String jobImgFile, Double workLatitude,
                                Double workLongitude, Integer cropLevel) {
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
        this.jobImgFile = jobImgFile;
    }
    public JobDetailResponseDTO() {
        // 기본 생성자
    }

    public Integer getCropLevel() {
        return cropLevel;
    }

    public void setCropLevel(Integer cropLevel) {
        this.cropLevel = cropLevel;
    }

    public Double getWorkLongitude() {
        return workLongitude;
    }

    public void setWorkLongitude(Double workLongitude) {
        this.workLongitude = workLongitude;
    }

    public Double getWorkLatitude() {
        return workLatitude;
    }

    public void setWorkLatitude(Double workLatitude) {
        this.workLatitude = workLatitude;
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

    public String getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(String startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public String getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(String endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public String getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
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

    public String getStartRecruitDate() {
        return startRecruitDate;
    }

    public void setStartRecruitDate(String startRecruitDate) {
        this.startRecruitDate = startRecruitDate;
    }

    public String getEndRecruitDate() {
        return endRecruitDate;
    }

    public void setEndRecruitDate(String endRecruitDate) {
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

    public String getJobImgFile() {
        return jobImgFile;
    }

    public void setJobImgFile(String jobImgFile) {
        this.jobImgFile = jobImgFile;
    }

    public String getCreatedJobDate() {
        return createdJobDate;
    }

    public void setCreatedJobDate(String createdJobDate) {
        this.createdJobDate = createdJobDate;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
