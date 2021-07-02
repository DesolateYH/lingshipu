package com.example.demo.nxbb.model;

import lombok.Data;

@Data
public class ActivityDetailed {
    String activityLocation;
    int activityPeopleNumber;
    int activityCurrentAppointmentsNumber;
    int activityStatus;
    int id;
    String activityTime;

    @Data
    public static class plan{
        String activity_plan;
    }
}
