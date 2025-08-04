package org.example.schedulemanagement.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.schedulemanagement.dto.ScheduleRequest;
import org.example.schedulemanagement.dto.ScheduleRequestPassword;
import org.example.schedulemanagement.dto.ScheduleResponseSecret;
import org.example.schedulemanagement.dto.ScheduleUpdateRequest;
import org.example.schedulemanagement.entity.Schedule;
import org.example.schedulemanagement.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 일정 생성
    @Transactional
    public ScheduleResponseSecret createSchedule(ScheduleRequest scheduleRequest) {
        Schedule schedule = new Schedule(
                scheduleRequest.getTitle(),
                scheduleRequest.getDescription(),
                scheduleRequest.getUserName(),
                scheduleRequest.getPassword());
        Schedule saveSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseSecret( // 비밀번호는 출력되면 안되기 때문에 비밀번호는 제외
                saveSchedule.getId(),
                saveSchedule.getTitle(),
                saveSchedule.getDescription(),
                saveSchedule.getUserName(),
                saveSchedule.getCreateTime(),
                saveSchedule.getUpdateTime()
        );
    }

    // 일정 조회 (작성자 이름을 쿼리문으로 작성하여 같은 이름의 작성자가 적은 글을 모두 조회 가능)
    @Transactional(readOnly = true) // get은 오직 읽기만 하기 때문에 readOnly을 사용한다.
    public List<ScheduleResponseSecret> allSchedules(String userName) {
        List<Schedule> schedules = scheduleRepository.findAll(); // repository의 저장 값을 가져와 새로운 List에 담는다.
        if (userName != null && !userName.isEmpty()) { // 유저명을 입력받고 && 유저명이 비어있지 않을 때
            schedules = scheduleRepository.findByUserNameOrderByUpdateTimeDesc(userName); // 유저명을 받았을 때 입력받은 유저명에 매치되는 저장 값들을 수정시가으로 내림차순으로 정렬한다.
            if (schedules.isEmpty()) { // 입력한 작성자명이 저장되어 있지 않을 때 예외처리
                throw new EntityNotFoundException("작성자를 찾을 수 없습니다.");
            }
        }
        List<ScheduleResponseSecret> scheduleList = new ArrayList<>(); // 출력 값의 타입을 매칭하기 위해 새로우 ArrayList를 생성한다.
        for (Schedule schedule : schedules) { // ArrayList에 저장 값을 하나씩 옮겨준다.
            scheduleList.add(new ScheduleResponseSecret( // 조건에서 비밀번호는 출력하면 안된다고하여 responseSecret 클래스를 사용한다.
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getDescription(),
                    schedule.getUserName(),
                    schedule.getCreateTime(),
                    schedule.getUpdateTime()
            ));
        }
        return scheduleList;
    }

    // 일정 단건 조회
    @Transactional(readOnly = true)
    public ScheduleResponseSecret findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("입력하신 " + id + "번은 존재하지 않습니다.") // 예외 처리
        );
        return new ScheduleResponseSecret( // 비밀번호는 출력되면 안되기 때문에 비밀번호는 제외
                schedule.getId(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getUserName(),
                schedule.getCreateTime(),
                schedule.getUpdateTime()
        );
    }

    // 일정 수정
    @Transactional
    public ScheduleResponseSecret updateSchedule(Long id, ScheduleUpdateRequest updateRequest) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("입력하신 " + id + "번은 존재하지 않습니다.") //예외 처리
        );
        if (!schedule.getPassword().equals(updateRequest.getPassword())) {// 작성글의 비밀번호와 매칭하여 맞으면 수정 허용
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); // 비밀번호가 맞지 않을 경우 예외 처리
        }
        schedule.ScheduleUpdate(updateRequest.getTitle(), updateRequest.getUserName());
        return new ScheduleResponseSecret( // 비밀번호는 출력되면 안되기 때문에 비밀번호는 제외
                schedule.getId(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getUserName(),
                schedule.getCreateTime(),
                schedule.getUpdateTime()
        );
    }

    // 일정 삭제
    @Transactional
    public void deleteScheduleById(Long id, ScheduleRequestPassword scheduleRequestPassword) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("입력하신 " + id + "번은 존재하지 않습니다.") //예외 처리
        );
        if (!schedule.getPassword().equals(scheduleRequestPassword.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); // 비밀번호가 맞지 않을 경우 예외 처리
        }
        scheduleRepository.deleteById(id);
    }
}