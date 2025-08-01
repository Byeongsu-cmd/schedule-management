package org.example.schedulemanagement.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponse {

    private Long id;
    private String title; // 일정 제목
    private String description; // 일정 내용
    private String userName; // 작성자명
    private String password; // 비밀번호
    private LocalDateTime createTime; // 작성일
    private LocalDateTime updateTime; // 수정일

    public ScheduleResponse(Long id, String title, String description, String userName, String password, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userName = userName;
        this.password = password;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
