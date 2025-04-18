package com.tdtu.student_management_system.grade.service;

import com.tdtu.student_management_system.grade.dto.ScoreDTO;
import com.tdtu.student_management_system.grade.entity.Score;
import com.tdtu.student_management_system.grade.repository.ScoreRepository;
import com.tdtu.student_management_system.subject.entity.CourseOffering;
import com.tdtu.student_management_system.subject.repository.CourseOfferingRepository;
import com.tdtu.student_management_system.user.entity.User;
import com.tdtu.student_management_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    public Score addOrUpdateScore(ScoreDTO dto) {
        System.out.println(dto.getStudentId());
        User student = userRepository.findByUserId(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        CourseOffering offering = courseOfferingRepository.findById(dto.getCourseOfferingId())
                .orElseThrow(() -> new RuntimeException("Course Offering not found"));

        Score score = scoreRepository.findByCourseOffering_IdAndStudent_UserId(dto.getCourseOfferingId(), student.getUserId())
                .orElse(new Score());

        score.setStudent(student);
        score.setCourseOffering(offering);
        score.setProcess1(dto.getProcess1());
        score.setProcess2(dto.getProcess2());
        score.setMidterm(dto.getMidterm());
        score.setFinalExam(dto.getFinalExam());

        double avg = dto.getProcess1() * 0.1 + dto.getProcess2() * 0.2 + dto.getMidterm() * 0.2 + dto.getFinalExam() * 0.5;
        score.setAverage(avg);

        return scoreRepository.save(score);
    }

    public List<Score> getScoresByCourseOffering(Long offeringId) {
        return scoreRepository.findByCourseOfferingId(offeringId);
    }

    public Double getAverageByStudentId(String studentId){
        User student = userRepository.findByRoleAndUserId(User.Role.STUDENT, studentId)
                .orElseThrow(() -> new RuntimeException("Not found this student or this user is not a student!"));
        return scoreRepository.findAverageByStudentId(student.getId());
    }
}
