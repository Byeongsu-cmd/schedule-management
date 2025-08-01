package org.example.schedulemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedulemanagement.dto.ScheduleRequest;
import org.example.schedulemanagement.dto.ScheduleResponse;
import org.example.schedulemanagement.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponse> createSchedule(
            @RequestBody ScheduleRequest scheduleRequest
    ) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleRequest));
    }
}
