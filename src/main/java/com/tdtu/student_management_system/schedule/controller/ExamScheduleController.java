package com.tdtu.student_management_system.schedule.controller;

import com.tdtu.student_management_system.schedule.dto.ExamScheduleDTO;
import com.tdtu.student_management_system.schedule.entity.ExamSchedule;
import com.tdtu.student_management_system.schedule.service.ExamScheduleService;
import com.tdtu.student_management_system.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam-schedules")
public class ExamScheduleController {

    @Autowired
    private ExamScheduleService examScheduleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ExamSchedule> create(@RequestBody ExamScheduleDTO dto) {
        try {
            ExamSchedule examSchedule = examScheduleService.createExamSchedule(dto);
            return new ResponseEntity<>(examSchedule, HttpStatus.CREATED);
        } catch (Exception e){
            System.err.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateExamSchedule(@PathVariable Long id, @RequestBody ExamScheduleDTO dto){
        ExamSchedule examSchedule = examScheduleService.updateExam(id, dto);
        if(examSchedule != null){
            return new ResponseEntity<>(examSchedule, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Update exam failed!", HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExamSchedule(@PathVariable Long id){
        try{
            examScheduleService.deleteExam(id);
            return ResponseEntity.ok("This exam schedule deleted successfully!");
        }catch(RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/schedule-exam/{courseOfferingId}")
    public ResponseEntity<List<ExamSchedule>> getByCourse(@PathVariable Long courseOfferingId) {
        return ResponseEntity.ok(examScheduleService.getSchedulesByCourseOffering(courseOfferingId));
    }
}
