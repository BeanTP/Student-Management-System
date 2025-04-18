package com.tdtu.student_management_system.schedule.repository;

import com.tdtu.student_management_system.schedule.entity.ExamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, Long> {
    List<ExamSchedule> findByCourseOfferingId(Long courseOfferingId);
}
