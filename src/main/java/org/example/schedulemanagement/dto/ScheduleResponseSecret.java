package org.example.schedulemanagement.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseSecret {

    private final Long id; //일정 고유 번호
    private final String title; // 일정 제목
    private final String description; // 일정 내용
    private final String userName; // 작성자명
    private final LocalDateTime createTime; // 작성일
    private final LocalDateTime updateTime; // 수정일

    public ScheduleResponseSecret(
            Long id,
            String title,
            String description,
            String userName,
            LocalDateTime createTime,
            LocalDateTime updateTime
    ){
        this.id = id;
        this.title = title;
        this.description = description;
        this.userName = userName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
