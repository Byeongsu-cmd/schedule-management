package org.example.schedulemanagement.dto;

import lombok.Getter;

@Getter
public class ScheduleUpdateRequest {
    private String title; // 일정 제목
    private String userName; // 작성자
    private String password; // 비밀번호
}
