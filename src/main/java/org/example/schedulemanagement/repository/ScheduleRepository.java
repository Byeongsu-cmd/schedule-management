package org.example.schedulemanagement.repository;

import org.example.schedulemanagement.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserNameOrderByUpdateTimeDesc(String userName); // 작성자의 이름을 조회한 후 수정시간을 기준으로 내림차순 정렬
}
