package com.tdtu.student_management_system.schedule.entity;

import com.tdtu.student_management_system.subject.entity.CourseOffering;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "exam_schedules")
public class ExamSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;

    @ManyToOne
    @JoinColumn(name = "course_offering_id")
    private CourseOffering courseOffering;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public CourseOffering getCourseOffering() {
        return courseOffering;
    }

    public void setCourseOffering(CourseOffering courseOffering) {
        this.courseOffering = courseOffering;
    }
}
