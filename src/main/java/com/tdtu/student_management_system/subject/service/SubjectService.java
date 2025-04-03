package com.tdtu.student_management_system.subject.service;

import com.tdtu.student_management_system.subject.dto.SubjectDTO;
import com.tdtu.student_management_system.subject.entity.Subject;
import com.tdtu.student_management_system.subject.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public Subject createSubject(SubjectDTO subjectDTO){
        if(subjectDTO.getSubjectId() == 0)
            throw new RuntimeException("Invalid subjectId provided.");

        if(subjectRepository.findBySubjectId(subjectDTO.getSubjectId()).isPresent()){
            throw new RuntimeException("Subject ID already exists.");
        }

        Subject subject = new Subject();
        subject.setSubjectId(subjectDTO.getSubjectId());
        subject.setSubjectName(subjectDTO.getSubjectName());
        subject.setCredit(subjectDTO.getCredit());

        return subjectRepository.save(subject);
    }

    public Subject updateSubject(long subjectId, SubjectDTO subjectDTO){
        Optional<Subject> existingSubject = subjectRepository.findBySubjectId(subjectId);
        if(existingSubject.isPresent()){
            Subject subject = existingSubject.get();

            if(subjectDTO.getSubjectId() != 0 && subjectDTO.getSubjectId() != subject.getSubjectId())
                subject.setSubjectId(subjectDTO.getSubjectId());

            if(subjectDTO.getSubjectName() != null && !subjectDTO.getSubjectName().equals(subject.getSubjectName()))
                subject.setSubjectName(subjectDTO.getSubjectName());

            if(subjectDTO.getCredit() != 0 && subjectDTO.getCredit() != subject.getCredit())
                subject.setCredit(subjectDTO.getCredit());

            return subjectRepository.save(subject);
        } else {
            throw new RuntimeException("Subject not found with id: " + subjectId);
        }
    }

    public void delSubject(long subjectId){
        Subject subject = getSubjectById(subjectId);
        subjectRepository.delete(subject);
    }

    public List<Subject> getAllSubject(){return subjectRepository.findAll();}

    public Subject getSubjectById(long subjectId){
        Optional<Subject> subject = subjectRepository.findBySubjectId(subjectId);
        if(subject.isPresent()){
            return subject.get();
        } else {
            throw new RuntimeException("Subject not found with id: " + subjectId);
        }
    }
}
