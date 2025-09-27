package com.iplus.studentManagement.repository;

import com.iplus.studentManagement.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Optional: add custom query methods if needed
    boolean existsByCode(String code); // e.g., to check if course code already exists
}
