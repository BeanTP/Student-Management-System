package com.tdtu.student_management_system.subject.repository;

import com.tdtu.student_management_system.subject.entity.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseOfferingRepository extends JpaRepository<CourseOffering, Long> {
}
