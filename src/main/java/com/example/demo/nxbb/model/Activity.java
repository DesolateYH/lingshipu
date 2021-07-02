package com.example.demo.nxbb.model;


import lombok.Data;

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
