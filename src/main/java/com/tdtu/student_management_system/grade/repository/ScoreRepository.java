package com.tdtu.student_management_system.grade.repository;

import com.tdtu.student_management_system.grade.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByCourseOfferingId(Long courseOfferingId);
    Optional<Score> findByCourseOffering_IdAndStudent_UserId(Long courseOfferingId, String studentId);

    @Query("SELECT AVG(s.average) FROM Score s WHERE s.student.id = :studentId")
    Double findAverageByStudentId(@Param("studentId") Long studentId);
}
