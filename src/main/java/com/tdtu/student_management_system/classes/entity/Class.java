package com.tdtu.student_management_system.classes.entity;

import com.tdtu.student_management_system.user.entity.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "classes")
public class Class {
    @Id
    private long id;
    private String className;
    private String academicYear;
    private String teacherId;
//    @ElementCollection
    private List<String> students;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
}
