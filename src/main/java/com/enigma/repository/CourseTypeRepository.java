package com.enigma.repository;

import com.enigma.model.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTypeRepository extends JpaRepository<CourseType, String> {
}
