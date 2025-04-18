package com.tdtu.student_management_system.classes.service;

import com.tdtu.student_management_system.classes.dto.ClassDTO;
import com.tdtu.student_management_system.classes.entity.Class;
import com.tdtu.student_management_system.classes.repository.ClassRepository;
import com.tdtu.student_management_system.user.entity.User;
import com.tdtu.student_management_system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private UserRepository userRepository;

    public Class createClass(ClassDTO classDTO){
        Optional<User> teacher = userRepository.findByRoleAndUserId(User.Role.TEACHER, classDTO.getTeacherId());
        if(teacher.isEmpty()){
            throw new RuntimeException("Not found teacherId with: " + classDTO.getTeacherId());
        }

        List<String> students = new ArrayList<>();
        for(String studentId : classDTO.getStudents()){
            User student = userRepository.findByUserId(studentId)
                    .orElseThrow(() -> new RuntimeException("Student ID: " + studentId + "not found!"));
            students.add(student.getUserId());
        }

        Class classEntity = new Class();
        classEntity.setId(classDTO.getId());
        classEntity.setClassName(classDTO.getClassName());
        classEntity.setTeacherId(classDTO.getTeacherId());
        classEntity.setAcademicYear(classDTO.getAcademicYear());
        classEntity.setStudents(students);

        return classRepository.save(classEntity);
    }

    public Class addStudentsToClass(long classId, ClassDTO classDTO){
        Optional<Class> existingClass = classRepository.findById(classId);
        if(existingClass.isPresent()){
            Class currClass = existingClass.get();

            List<String> students = currClass.getStudents();
            for(String studentId : classDTO.getStudents()){
                User student = userRepository.findByUserId(studentId)
                        .orElseThrow(() -> new RuntimeException("Student ID: " + studentId + "not found!"));
                if(!students.contains(student.getUserId()))
                    students.add(student.getUserId());
            }

            currClass.setStudents(students);
            return classRepository.save(currClass);
        } else {
            throw new RuntimeException("Class not found with id: " + classId);
        }
    }

    public Class updateTeacher(long classId, String teacherId){
        Class exitingClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + classId));

        Optional<User> teacher = userRepository.findByRoleAndUserId(User.Role.TEACHER, teacherId);
        if(teacher.isEmpty())
            throw new RuntimeException("Teacher with id: " + teacherId + " not found or is not a TEACHER!");
        exitingClass.setTeacherId(teacherId);
        return classRepository.save(exitingClass);
    }

    public Class delStudentFromClass(long classId, ClassDTO classDTO){
        Class currClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + classId));

        List<String> students = currClass.getStudents();
        boolean modified = false;

        for(String studentId : classDTO.getStudents()){
            User student = userRepository.findByUserId(studentId)
                    .orElseThrow(() -> new RuntimeException("Student ID: " + studentId + "not found!"));

            if(students.contains(student.getUserId())){
                students.remove(student.getUserId());
                modified = true;
            }
        }

        if(modified){
            currClass.setStudents(students);
            return classRepository.save(currClass);
        } else {
            throw new RuntimeException("No matching students found in class.");
        }
    }

    public List<Class> getAllClass(){return classRepository.findAll();}

    public Class getClassById(long classId){
        Optional<Class> classById = classRepository.findById(classId);
        if(classById.isPresent()){
            return classById.get();
        }else{
            throw new RuntimeException("Not found class with Id: " + classId);
        }
    }

    public User getTeacherByClassId(long classId){
        Class teacherOfClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + classId));

        return userRepository.findByUserId(teacherOfClass.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found with userId: " + teacherOfClass.getTeacherId()));
    }

    public List<String> getStudentsByClassId(long classId){
        Class studentsOfClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with classId: " + classId));
        return studentsOfClass.getStudents();
    }

}
