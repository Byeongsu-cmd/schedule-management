package org.example.schedulemanagement.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.schedulemanagement.dto.ScheduleRequest;
import org.example.schedulemanagement.dto.ScheduleResponse;
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

    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest) {
        Schedule schedule = new Schedule(
                scheduleRequest.getTitle(),
                scheduleRequest.getDescription(),
                scheduleRequest.getUserName(),
                scheduleRequest.getPassword());
        Schedule saveSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponse(
                saveSchedule.getId(),
                saveSchedule.getTitle(),
                saveSchedule.getDescription(),
                saveSchedule.getUserName(),
                saveSchedule.getPassword(),
                saveSchedule.getCreateTime(),
                saveSchedule.getUpdateTime()
        );
    }

    @Transactional(readOnly = true) // get은 오직 읽기만 하기 때문에 readOnly을 사용한다.
    public List<ScheduleResponseSecret> allSchedules(String userName) {
        List<Schedule> schedules = scheduleRepository.findAll(); // repository의 저장 값을 가져와 새로운 List에 담는다.
        if (userName != null && !userName.isEmpty()) { // 유저명을 입력받고 && 유저명이 비어있지 않을 때
            schedules = scheduleRepository.findByUserNameOrderByUpdateTimeDesc(userName); // 유저명을 받았을 때 입력받은 유저명에 매치되는 저장 값들을 수정시가으로 내림차순으로 정렬한다.
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

    @Transactional(readOnly = true)
    public ScheduleResponseSecret findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("입력하신 " + id + "번은 존재하지 않습니다.") // 예외 처리
        );
        return new ScheduleResponseSecret(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getUserName(),
                schedule.getCreateTime(),
                schedule.getUpdateTime()
        );
    }

    @Transactional
    public ScheduleResponseSecret updateSchedule(Long id, ScheduleUpdateRequest updateRequest) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("입력하신 " + id + "번은 존재하지 않습니다.") //예외 처리
        );
        if (schedule.getPassword().equals(updateRequest.getPassword())) { // 작성글의 비밀번호와 매칭하여 맞으면 수정 허용
            schedule.ScheduleUpdate(updateRequest.getTitle(),updateRequest.getUserName());
        }
        return new ScheduleResponseSecret(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getUserName(),
                schedule.getCreateTime(),
                schedule.getUpdateTime()
        );
    }

    @Transactional
    public void deleteScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("입력하신 " + id + "번은 존재하지 않습니다.") //예외 처리
        );
        scheduleRepository.delete(schedule);
    }
}

//
//### Lv 4. 일정 삭제  `필수`
//
//- [ ]  **선택한 일정 삭제**
//    - [ ]  선택한 일정을 삭제할 수 있습니다.
//        - [ ]  서버에 일정 삭제을 요청할 때 `비밀번호`를 함께 전달합니다.

//### Lv 5. 댓글 생성 `도전`
//
//- [ ]  **댓글 생성(댓글 작성하기)**
//    - [ ]  일정에 댓글을 작성할 수 있습니다.
//    - [ ]  댓글 생성 시, 포함되어야할 데이터
//        - [ ]  `댓글 내용`, `작성자명`, `비밀번호`, `작성/수정일`, `일정 고유식별자(ID)`를 저장
//        - [ ]  `작성/수정일`은 날짜와 시간을 모두 포함한 형태
//    - [ ]  각 일정의 고유 식별자(ID)를 자동으로 생성하여 관리
//    - [ ]  최초 생성 시, `수정일`은 `작성일`과 동일
//    - [ ]  `작성일`, `수정일` 필드는 `JPA Auditing`을 활용하여 적용합니다.
//    - [ ]  하나의 일정에는 댓글을 10개까지만 작성할 수 있습니다.
//    - [ ]  API 응답에 `비밀번호`는 제외해야 합니다.
//
//### Lv 6. 일정 단건 조회 업그레이드  `도전`
//
//- [ ]  **일정 단건 조회 업그레이드**
//    - [ ]  일정 단건 조회 시, 해당 일정에 등록된 댓글들을 포함하여 함께 응답합니다.
//    - [ ]  API 응답에 `비밀번호`는 제외해야 합니다.
//
//### Lv 7. 유저의 입력에 대한 검증 수행  `도전`
//
//- [ ]  설명
//    - [ ]  잘못된 입력이나 요청을 방지할 수 있습니다.
//    - [ ]  데이터의 `무결성을 보장`하고 애플리케이션의 예측 가능성을 높여줍니다.
//- [ ]  조건
//    - [ ]  `일정 제목`은 최대 30자 이내로 제한, 필수값 처리
//    - [ ]  `일정 내용`은 최대 200자 이내로 제한, 필수값 처리
//    - [ ]  `댓글 내용`은 최대 100자 이내로 제한, 필수값 처리
//    - [ ]  `비밀번호`, `작성자명`은 필수값 처리
//    - [ ]  `비밀번호`가 일치하지 않을 경우 적절한 오류 코드 및 메세지를 반환해야 합니다.

//1. 3 Layer Architecture(Controller, Service, Repository)를 적절히 적용했는지 확인해 보고, 왜 이러한 구조가 필요한지 작성해 주세요.
//2. `@RequestParam`, `@PathVariable`, `@RequestBody` 이 각각 어떤 어노테이션인지, 어떤 특징을 갖고 있는지 작성해 주세요.