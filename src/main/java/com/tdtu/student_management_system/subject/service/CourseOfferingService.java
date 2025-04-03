package com.tdtu.student_management_system.subject.service;

import com.tdtu.student_management_system.subject.dto.CourseOfferingDTO;
import com.tdtu.student_management_system.subject.entity.CourseOffering;
import com.tdtu.student_management_system.subject.entity.Subject;
import com.tdtu.student_management_system.subject.repository.CourseOfferingRepository;
import com.tdtu.student_management_system.subject.repository.SubjectRepository;
import com.tdtu.student_management_system.user.entity.User;
import com.tdtu.student_management_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseOfferingService {
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;

    public CourseOffering createCourse(long subjectId, CourseOfferingDTO courseOfferingDTO){
        Subject subject = subjectRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found!"));
        User teacher = userRepository.findByRoleAndUserId(User.Role.TEACHER, courseOfferingDTO.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found or user is not a teacher!"));

        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setSemester(courseOfferingDTO.getSemester());
        courseOffering.setSubject(subject);
        courseOffering.setTeacher(teacher);
        courseOffering.setStudents(List.of());

        return courseOfferingRepository.save(courseOffering);
    }

//    public CourseOffering registerStudent(long courseOfferingId, CourseOfferingDTO courseOfferingDTO){}
}
