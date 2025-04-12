package com.tdtu.student_management_system.grade.controller;

import com.tdtu.student_management_system.grade.dto.ScoreDTO;
import com.tdtu.student_management_system.grade.entity.Score;
import com.tdtu.student_management_system.grade.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/scores")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping
    public ResponseEntity<?> addOrUpdateScore(@RequestBody ScoreDTO dto) {
        return ResponseEntity.ok(scoreService.addOrUpdateScore(dto));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/course-offering/{id}")
    public ResponseEntity<List<Score>> getScoresByCourseOffering(@PathVariable Long id) {
        return ResponseEntity.ok(scoreService.getScoresByCourseOffering(id));
    }

    @GetMapping("/average/{studentId}")
    public ResponseEntity<Double> getAverageByStudent(@PathVariable String studentId){
        return ResponseEntity.ok(scoreService.getAverageByStudentId(studentId));
    }
}
