package org.example.schedulemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedulemanagement.dto.ScheduleRequest;
import org.example.schedulemanagement.dto.ScheduleRequestPassword;
import org.example.schedulemanagement.dto.ScheduleResponseSecret;
import org.example.schedulemanagement.dto.ScheduleUpdateRequest;
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
    public ResponseEntity<ScheduleResponseSecret> createSchedule(
            @RequestBody ScheduleRequest scheduleRequest
    ) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleRequest));
    }

    // 일정 전체 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponseSecret>> getAllSchedules(
            @RequestParam(required = false) String userName
    ) { // 작성자명은 조회 조건으로 포함될 수도 있고, 포함되지 않을 수도 있습니다. 기능 추가해야합니다.
        return ResponseEntity.ok(scheduleService.allSchedules(userName));
    }

    // 일정 단일 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseSecret> findScheduleById(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(scheduleService.findScheduleById(scheduleId));
    }
    // 일정 수정

    @PatchMapping("/schedules/{scheduleId}") //부분 수정이기 때문에 Put이 아닌 Patch 사용
    public ResponseEntity<ScheduleResponseSecret> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleUpdateRequest scheduleUpdateRequest
    ) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, scheduleUpdateRequest));
    }

    // 일정 삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public void deleteScheduleById(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequestPassword scheduleRequestPassword // 비밀번호의 입력 값을 받기위한 클래스
    ) {
        scheduleService.deleteScheduleById(scheduleId, scheduleRequestPassword);
    }
}
