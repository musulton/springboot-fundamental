package com.enigma.service;

import com.enigma.model.Course;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    List<Course> list() throws Exception;
    Course create(Course course) throws Exception;
    Course get(String id) throws Exception;
    void update(Course course, String id) throws Exception;
    void delete(String id) throws Exception;

    List<Course> getBy(String keyword, String value) throws Exception;

    Page<Course> list(Integer page, Integer size, String direction, String sortBy) throws Exception;
}
