package org.example.schedulemanagement.dto;

import lombok.Getter;

@Getter
public class ScheduleRequest {

    private String title; //일정 제목
    private String description; //일정 내용
    private String userName;
    private String password;
}
