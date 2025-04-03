package com.tdtu.student_management_system.subject.controller;

import com.tdtu.student_management_system.subject.dto.CourseOfferingDTO;
import com.tdtu.student_management_system.subject.entity.CourseOffering;
import com.tdtu.student_management_system.subject.service.CourseOfferingService;
import com.tdtu.student_management_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
