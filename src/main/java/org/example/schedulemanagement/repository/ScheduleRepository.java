package org.example.schedulemanagement.repository;

import org.example.schedulemanagement.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserNameOrderByUpdateTimeDesc(String userName);
}
