package com.tdtu.student_management_system.subject.repository;

import com.tdtu.student_management_system.subject.entity.Subject;
import com.tdtu.student_management_system.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findBySubjectId(long subjectId);
}
