package com.example.hero.object;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Job {
    int _id;
    String job_name;
    String user_id;
    Timestamp created_job_date;
    Timestamp updated_job_date;
    int area_id;
    Date start_work_date;
    Date end_work_date;
    Time start_work_time;
    Time end_work_time;
    String work_day;
    int pay;
    int crop_id;
    int recruit_count;
    Date start_recruit_date;
    Date end_recruit_date;
    String age;
    String career;
    String spec;
    String job_intro;
    String work_address;
    String work_address_detail;
    int file_id;
    String state;


    public Job(int _id, String job_name, String user_id, Timestamp created_job_date, Timestamp updated_job_date, int area_id, Date start_work_date, Date end_work_date, Time start_work_time, Time end_work_time, String work_day, int pay, int crop_id, int recruit_count, Date start_recruit_date, Date end_recruit_date, String age, String career, String spec, String job_intro, String work_address, String work_address_detail, int file_id, String state) {
        this._id = _id;
        this.job_name = job_name;
        this.user_id = user_id;
        this.created_job_date = created_job_date;
        this.updated_job_date = updated_job_date;
        this.area_id = area_id;
        this.start_work_date = start_work_date;
        this.end_work_date = end_work_date;
        this.start_work_time = start_work_time;
        this.end_work_time = end_work_time;
        this.work_day = work_day;
        this.pay = pay;
        this.crop_id = crop_id;
        this.recruit_count = recruit_count;
        this.start_recruit_date = start_recruit_date;
        this.end_recruit_date = end_recruit_date;
        this.age = age;
        this.career = career;
        this.spec = spec;
        this.job_intro = job_intro;
        this.work_address = work_address;
        this.work_address_detail = work_address_detail;
        this.file_id = file_id;
        this.state = state;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
