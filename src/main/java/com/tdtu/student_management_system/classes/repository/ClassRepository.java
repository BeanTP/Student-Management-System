package com.tdtu.student_management_system.classes.repository;

import com.tdtu.student_management_system.classes.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    Optional<Class> findById(Long id);
}
