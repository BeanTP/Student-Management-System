package com.tdtu.student_management_system.subject.controller;

import com.tdtu.student_management_system.subject.dto.SubjectDTO;
import com.tdtu.student_management_system.subject.entity.Subject;
import com.tdtu.student_management_system.subject.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-subject")
    public ResponseEntity<Subject> createSubject(@RequestBody SubjectDTO subjectDTO){
        try{
            Subject createdSubject = subjectService.createSubject(subjectDTO);
            return new ResponseEntity<>(createdSubject, HttpStatus.CREATED);
        }catch(Exception e){
            System.err.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{subjectId}")
    public ResponseEntity<?> updateSubject(@PathVariable long subjectId, @RequestBody SubjectDTO subjectDTO){
        Subject updatedSubject = subjectService.updateSubject(subjectId, subjectDTO);
        if(updatedSubject != null){
            return new ResponseEntity<>(updatedSubject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Subject not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{subjectId}")
    public ResponseEntity<String> delSubject(@PathVariable long subjectId){
        try{
            subjectService.delSubject(subjectId);
            return ResponseEntity.ok("Subject deleted successfully!");
        }catch(RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<Subject> getAllSubject() {return subjectService.getAllSubject();}

    @GetMapping("/search/{subjectId}")
    public Subject getSubjectById(@PathVariable long subjectId){return subjectService.getSubjectById(subjectId);}
}
