package com.tdtu.student_management_system.tuition.repository;

import com.tdtu.student_management_system.tuition.entity.TuitionFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TuitionFeeRepository extends JpaRepository<TuitionFee, Long> {
    List<TuitionFee> findByStudentId(String studentId);
    Optional<TuitionFee> findByStudentIdAndSemester(String studentId, String semester);
}
