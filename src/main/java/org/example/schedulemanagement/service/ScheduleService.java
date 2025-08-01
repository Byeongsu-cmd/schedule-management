package org.example.schedulemanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulemanagement.dto.ScheduleRequest;
import org.example.schedulemanagement.dto.ScheduleResponse;
import org.example.schedulemanagement.entity.Schedule;
import org.example.schedulemanagement.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
//
//### Lv 1. 일정 생성  `필수`
//
//- [ ]  **일정 생성(일정 작성하기)**
//        - [ ]  `작성/수정일`은 날짜와 시간을 모두 포함한 형태
//    - [ ]  각 일정의 고유 식별자(ID)를 자동으로 생성하여 관리
//    - [ ]  최초 생성 시, `수정일`은 `작성일`과 동일
//    - [ ]  API 응답에 `비밀번호`는 제외해야 합니다.
//
//### Lv 2. 일정 조회  `필수`
//
//- [ ]  **전체 일정 조회**
//    - [ ]  `작성자명`을 기준으로 등록된 일정 목록을 전부 조회
//        - [ ]  `작성자명`은 조회 조건으로 포함될 수도 있고, 포함되지 않을 수도 있습니다.
//        - [ ]  하나의 API로 작성해야 합니다.
//    - [ ]  `수정일` 기준 내림차순으로 정렬
//    - [ ]  API 응답에 `비밀번호`는 제외해야 합니다.
//- [ ]  **선택 일정 조회**
//    - [ ]  선택한 일정 단건의 정보를 조회할 수 있습니다.
//        - [ ]  일정의 고유 식별자(ID)를 사용하여 조회합니다.
//    - [ ]  API 응답에 `비밀번호`는 제외해야 합니다.
//
//### Lv 3. 일정 수정  `필수`
//
//- [ ]  **선택한 일정 수정**
//    - [ ]  선택한 일정 내용 중 `일정 제목`, `작성자명` 만 수정 가능
//        - [ ]  서버에 일정 수정을 요청할 때 `비밀번호`를 함께 전달합니다.
//        - [ ]  `작성일` 은 변경할 수 없으며, `수정일` 은 수정 완료 시, 수정한 시점으로 변경되어야 합니다.
//    - [ ]  API 응답에 `비밀번호`는 제외해야 합니다.
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