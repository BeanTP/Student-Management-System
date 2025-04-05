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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        courseOffering.setGroupCourse(courseOfferingDTO.getGroupCourse());
        courseOffering.setShift(courseOfferingDTO.getShift());
        courseOffering.setDayOfWeek(courseOfferingDTO.getDayOfWeek());
        courseOffering.setSubject(subject);
        courseOffering.setTeacher(teacher);
        courseOffering.setStudents(List.of());

        return courseOfferingRepository.save(courseOffering);
    }

    public CourseOffering assignTeacher(long subjectId, String teacherId, CourseOfferingDTO courseOfferingDTO){
        Subject subject = subjectRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found!"));
        User teacher = userRepository.findByRoleAndUserId(User.Role.TEACHER, teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found or user is not a teacher!"));

        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setSemester(courseOfferingDTO.getSemester());
        courseOffering.setGroupCourse(courseOfferingDTO.getGroupCourse());
        courseOffering.setShift(courseOfferingDTO.getShift());
        courseOffering.setDayOfWeek(courseOfferingDTO.getDayOfWeek());
        courseOffering.setSubject(subject);
        courseOffering.setTeacher(teacher);
        courseOffering.setStudents(List.of());

        return courseOfferingRepository.save(courseOffering);
    }

    public CourseOffering registerStudentToCourse(long courseOfferingId, String studentId){
        CourseOffering courseOffering = courseOfferingRepository.findById(courseOfferingId)
                .orElseThrow(() -> new RuntimeException("Course offering not found!"));

        User student = userRepository.findByRoleAndUserId(User.Role.STUDENT, studentId)
                .orElseThrow(() -> new RuntimeException("Student not found or not a student!"));

        List<User> students = courseOffering.getStudents();
        if(!students.contains(student))
            students.add(student);
        courseOffering.setStudents(students);
        return courseOfferingRepository.save(courseOffering);
    }

    public CourseOffering updateCourse(long courseOfferingId, CourseOfferingDTO courseOfferingDTO){
        Optional<CourseOffering> existingCourse = courseOfferingRepository.findById(courseOfferingId);
        if(existingCourse.isPresent()){
            CourseOffering course = existingCourse.get();

            if(courseOfferingDTO.getDayOfWeek() != null && !courseOfferingDTO.getDayOfWeek().equals(course.getDayOfWeek()))
                course.setDayOfWeek(courseOfferingDTO.getDayOfWeek());

            if(courseOfferingDTO.getShift() != 0 && courseOfferingDTO.getShift() != course.getShift())
                course.setShift(courseOfferingDTO.getShift());

            if(courseOfferingDTO.getGroupCourse() != null && !courseOfferingDTO.getGroupCourse().equals(course.getGroupCourse()))
                course.setGroupCourse(courseOfferingDTO.getGroupCourse());

            return courseOfferingRepository.save(course);
        } else {
            throw new RuntimeException("Course not found in system!");
        }
    }

    public void removeStudentFromCourse(long courseOfferingId, List<String> studentIds){
        CourseOffering currCourse = courseOfferingRepository.findById(courseOfferingId)
                .orElseThrow(() -> new RuntimeException("This course not found in system!"));

        List<User> students = currCourse.getStudents();
        List<User> studentsToRemove = new ArrayList<>();
        for(String studentId: studentIds){
            User student = userRepository.findByUserId(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found in course!"));
            if(students.contains(student))
                studentsToRemove.add(student);
        }
        students.removeAll(studentsToRemove);
        currCourse.setStudents(students);
        courseOfferingRepository.save(currCourse);
    }

    public List<CourseOffering> getAllCourseOffering(){return courseOfferingRepository.findAll();}

    public List<CourseOffering> getCourseBySemester(String semester){
        List<CourseOffering> currCourseOfSemester = courseOfferingRepository.findCourseBySemester(semester);
        if(currCourseOfSemester.isEmpty())
            throw new RuntimeException("There are no courses open this semester! Please wait for the earliest announcement.");
        return currCourseOfSemester;
    }
}
