package com.iplus.studentManagement.repository;

import com.iplus.studentManagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Optional: check if email already exists
    boolean existsByEmail(String email);
}
