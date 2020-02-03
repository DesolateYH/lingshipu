package com.example.demo.nxb.model;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class Activity {
    String activityTitle;
    String activityDescription;
    int sort;
    String activityDeadline;
    String activityImageAddress;
    int id;
    int activity_people_number;
    int activity_current_appointments_number;
    double ratio;
}
