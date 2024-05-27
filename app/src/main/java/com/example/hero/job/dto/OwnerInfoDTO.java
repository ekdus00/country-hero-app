package com.example.hero.job.dto;

public class OwnerInfoDTO {
    private String userName;
    private String mail;
    private String workAddress;
    private String workAddressDetail;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
