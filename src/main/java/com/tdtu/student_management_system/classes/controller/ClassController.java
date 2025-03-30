package com.tdtu.student_management_system.classes.controller;

import com.tdtu.student_management_system.classes.dto.ClassDTO;
import com.tdtu.student_management_system.classes.entity.Class;
import com.tdtu.student_management_system.classes.service.ClassService;
import com.tdtu.student_management_system.user.dto.UserDTO;
import com.tdtu.student_management_system.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassController {
    @Autowired
    private ClassService classService;

    @PostMapping
    public ResponseEntity<?> createdClass(@RequestBody ClassDTO classDTO){
        try{
            Class classEntity = classService.createClass(classDTO);
            return ResponseEntity.ok(classEntity);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Class> getAllClass(){return classService.getAllClass();}

    @GetMapping("/{classId}")
    public Class getClassById(@PathVariable long classId){return classService.getClassById(classId);}

    @GetMapping("/{classId}/teacher")
    public ResponseEntity<User> getTeacherByClassId(@PathVariable long classId){
        User teacher = classService.getTeacherByClassId(classId);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/{classId}/students")
    public ResponseEntity<List<String>> getStudentsByClassId(@PathVariable long classId){
        List<String> students = classService.getStudentsByClassId(classId);
        return ResponseEntity.ok(students);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{classId}/students")
    public ResponseEntity<?> addStudentToClass(@PathVariable long classId, @RequestBody ClassDTO classDTO){
        Class updatedClass = classService.addStudentsToClass(classId, classDTO);

        if(updatedClass != null){
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Add students to class fail!", HttpStatus.BAD_REQUEST);
        }
    }
}
