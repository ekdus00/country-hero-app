package com.example.hero;

import com.example.hero.object.Job;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class JobListResult {
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

    ArrayList<Job> dailyJobListResult = new ArrayList<Job>();
}
