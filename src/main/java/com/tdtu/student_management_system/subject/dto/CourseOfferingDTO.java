package com.tdtu.student_management_system.subject.dto;

import java.util.List;

public class CourseOfferingDTO {
    private long id;
    private String teacherId;
    private String semester;

    private String groupCourse;
    private int shift;
    private String dayOfWeek;
    private List<String> students;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public String getGroupCourse() {
        return groupCourse;
    }

    public void setGroupCourse(String groupCourse) {
        this.groupCourse = groupCourse;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
