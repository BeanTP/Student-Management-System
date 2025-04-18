package com.tdtu.student_management_system.subject.repository;

import com.tdtu.student_management_system.subject.entity.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseOfferingRepository extends JpaRepository<CourseOffering, Long> {
    List<CourseOffering> findCourseBySemester(String semester);
    List<CourseOffering> findBySemesterAndStudentsUserId(String semester, String userId);

}
