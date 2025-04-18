package com.tdtu.student_management_system.subject.controller;

import com.tdtu.student_management_system.subject.dto.CourseOfferingDTO;
import com.tdtu.student_management_system.subject.entity.CourseOffering;
import com.tdtu.student_management_system.subject.service.CourseOfferingService;
import com.tdtu.student_management_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class CourseOfferingController {
    @Autowired
    private CourseOfferingService courseOfferingService;
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-course/{subjectId}")
    public ResponseEntity<CourseOffering> createCourse(@PathVariable long subjectId, @RequestBody CourseOfferingDTO courseOfferingDTO){
        try{
            CourseOffering createdCourse = courseOfferingService.createCourse(subjectId, courseOfferingDTO);
            return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
        }catch(Exception e){
            System.err.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/assign-course/{subjectId}")
    public ResponseEntity<CourseOffering> assignTeacherCourse(@PathVariable long subjectId, @RequestBody CourseOfferingDTO courseOfferingDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        try{
            CourseOffering registered = courseOfferingService.assignTeacher(subjectId, userId, courseOfferingDTO);
            return new ResponseEntity<>(registered, HttpStatus.CREATED);
        }catch(Exception e){
            System.err.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/register-course/{courseOfferingId}")
    public ResponseEntity<CourseOffering> registerStudentToCourse(@PathVariable long courseOfferingId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        try{
            CourseOffering registered = courseOfferingService.registerStudentToCourse(courseOfferingId, userId);
            return new ResponseEntity<>(registered, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-course/{courseOfferingId}")
    public ResponseEntity<?> updateCourse(@PathVariable long courseOfferingId, @RequestBody CourseOfferingDTO courseOfferingDTO){
        CourseOffering updatedCourse = courseOfferingService.updateCourse(courseOfferingId, courseOfferingDTO);
        if(updatedCourse != null){
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed update!", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/remove-students/{courseOfferingId}")
    public ResponseEntity<String> removeStudentFromCourse(@PathVariable long courseOfferingId, @RequestBody CourseOfferingDTO courseOfferingDTO){
        try {
            courseOfferingService.removeStudentFromCourse(courseOfferingId, courseOfferingDTO.getStudents());
            return ResponseEntity.ok("Students removed from course successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/course")
    public List<CourseOffering> getAllCourse(){return courseOfferingService.getAllCourseOffering();}

    @GetMapping("/course/{semester}")
    public ResponseEntity<?> getCourseBySemester(@PathVariable String semester){
        try{
            List<CourseOffering> course = courseOfferingService.getCourseBySemester(semester);
            return ResponseEntity.ok(course);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

}
