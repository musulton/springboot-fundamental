package com.enigma.service;

import com.enigma.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> list() throws Exception;
    Course create(Course course) throws Exception;
    Course get(String id) throws Exception;
    void update(Course course, String id) throws Exception;
    void delete(String id) throws Exception;

    List<Course> getBy(String keyword, String value) throws Exception;
}
