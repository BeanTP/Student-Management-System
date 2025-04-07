package com.tdtu.student_management_system.schedule.service;

import com.tdtu.student_management_system.schedule.dto.ExamScheduleDTO;
import com.tdtu.student_management_system.schedule.entity.ExamSchedule;
import com.tdtu.student_management_system.schedule.repository.ExamScheduleRepository;
import com.tdtu.student_management_system.subject.entity.CourseOffering;
import com.tdtu.student_management_system.subject.repository.CourseOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamScheduleService {

    @Autowired
    private ExamScheduleRepository examScheduleRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    public ExamSchedule createExamSchedule(ExamScheduleDTO dto) {
        CourseOffering course = courseOfferingRepository.findById(dto.getCourseOfferingId())
                .orElseThrow(() -> new RuntimeException("Course offering not found"));

        ExamSchedule exam = new ExamSchedule();
        exam.setCourseOffering(course);
        exam.setExamDate(dto.getExamDate());
        exam.setStartTime(dto.getStartTime());
        exam.setEndTime(dto.getEndTime());
        exam.setRoom(dto.getRoom());

        return examScheduleRepository.save(exam);
    }

    public List<ExamSchedule> getSchedulesByCourseOffering(Long courseOfferingId) {
        return examScheduleRepository.findByCourseOfferingId(courseOfferingId);
    }

    public ExamSchedule updateExam(Long id, ExamScheduleDTO dto) {
        ExamSchedule exam = examScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam Schedule not found"));

        exam.setExamDate(dto.getExamDate());
        exam.setStartTime(dto.getStartTime());
        exam.setEndTime(dto.getEndTime());
        exam.setRoom(dto.getRoom());

        return examScheduleRepository.save(exam);
    }

    public void deleteExam(Long id) {
        examScheduleRepository.deleteById(id);
    }
}
