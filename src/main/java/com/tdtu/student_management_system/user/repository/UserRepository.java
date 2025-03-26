package com.tdtu.student_management_system.user.repository;

import com.tdtu.student_management_system.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    List<User> findByDepartmentCode(int departmentCode);
    List<User> findByRole(User.Role role);
    Optional<User> findByRoleAndUserId(User.Role role, String userId);

}
