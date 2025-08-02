package org.example.schedulemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedulemanagement.dto.ScheduleRequest;
import org.example.schedulemanagement.dto.ScheduleResponse;
import org.example.schedulemanagement.dto.ScheduleResponseSecret;
import org.example.schedulemanagement.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponse> createSchedule(
            @RequestBody ScheduleRequest scheduleRequest
    ) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleRequest));
    }

    // 일정 전체 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponseSecret>> getAllSchedules() { // 작성자명은 조회 조건으로 포함될 수도 있고, 포함되지 않을 수도 있습니다. 기능 추가해야합니다.
        return ResponseEntity.ok(scheduleService.allSchedules());
    }

    // 일정 단일 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseSecret> findScheduleById(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(scheduleService.findScheduleById(scheduleId));
    }
    // 일정 수정

    // 일정 삭제
}
