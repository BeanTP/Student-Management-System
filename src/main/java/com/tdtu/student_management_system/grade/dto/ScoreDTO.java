package com.tdtu.student_management_system.grade.dto;

public class ScoreDTO {
    private Long courseOfferingId;
    private String studentId;
    private Double process1;
    private Double process2;
    private Double midterm;
    private Double finalExam;

    public Long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Double getProcess1() {
        return process1;
    }

    public void setProcess1(Double process1) {
        this.process1 = process1;
    }

    public Double getProcess2() {
        return process2;
    }

    public void setProcess2(Double process2) {
        this.process2 = process2;
    }

    public Double getMidterm() {
        return midterm;
    }

    public void setMidterm(Double midterm) {
        this.midterm = midterm;
    }

    public Double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(Double finalExam) {
        this.finalExam = finalExam;
    }
}
