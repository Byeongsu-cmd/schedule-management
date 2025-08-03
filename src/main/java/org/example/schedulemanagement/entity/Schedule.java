package org.example.schedulemanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.schedulemanagement.dto.ScheduleUpdateRequest;

@Entity
@Getter
@RequiredArgsConstructor
public class Schedule extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 각 일정의 고유 식별자(ID)를 자동으로 생성하여 관리
    private String title; // 일정 제목
    private String description; // 일정 내용
    private String userName; // 작성자명
    private String password; // 비밀번호

    public Schedule(String title, String description, String userName, String password) {
        this.title = title;
        this.description = description;
        this.userName = userName;
        this.password = password;
    }

    public void ScheduleUpdate(String title, String userName) {
        this.title = title;
        this.userName = userName;
    }
}