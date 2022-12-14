package com.enigma.service;

import com.enigma.exception.NotFoundException;
import com.enigma.model.Course;
import com.enigma.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Value("3")
    Integer dataLength;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> list() throws Exception {
        List<Course> result = courseRepository.getAll();
        if (result.isEmpty()) {
            throw new NotFoundException();
        }
        return result;
    }

    @Override
    public Course create(Course course) throws Exception {
        if (!(courseRepository.getAll().size() < dataLength)) {
            throw new Exception("Data is full");
        }

        return courseRepository.create(course);
    }

    @Override
    public Course get(String id) throws Exception {
        Optional<Course> result = courseRepository.findById(id);
        if(result.isEmpty()) {
            throw new NotFoundException();
        }

        return result.get();
    }

    @Override
    public void update(Course course, String id) throws Exception {
        try {
            Course existingCourse = get(id);
            courseRepository.update(course, existingCourse.getCourseId());
        } catch (NotFoundException e) {
            throw new NotFoundException("Update failed because ID is not found");
        }
    }

    @Override
    public void delete(String id) {
        try {
            Course course = get(id);
            courseRepository.delete(course.getCourseId());
        } catch (Exception e) {
            throw new NotFoundException("Delete failed because ID is not found");
        }
    }

    @Override
    public List<Course> getBy(String keyword, String value) throws Exception {
        Optional<List<Course>> result = courseRepository.findBy(keyword, value);
        if (result.isEmpty()) {
            throw new NotFoundException("Course not found");
        }
        return result.get();
    }
}
