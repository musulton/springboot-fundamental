package com.enigma.service;

import com.enigma.exception.NotFoundException;
import com.enigma.model.Course;
import com.enigma.repository.CourseArrayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseArrayServiceImpl implements CourseService {

    @Value("3")
    Integer dataLength;

    @Autowired
    private CourseArrayRepository courseArrayRepository;

    @Override
    public List<Course> list() throws Exception {
        List<Course> result = courseArrayRepository.getAll();
        if (result.isEmpty()) {
            throw new NotFoundException();
        }
        return result;
    }

    @Override
    public Course create(Course course) throws Exception {
        if (!(courseArrayRepository.getAll().size() < dataLength)) {
            throw new Exception("Data is full");
        }

        return courseArrayRepository.create(course);
    }

    @Override
    public Course get(String id) throws Exception {
        Optional<Course> result = courseArrayRepository.findById(id);
        if(result.isEmpty()) {
            throw new NotFoundException();
        }

        return result.get();
    }

    @Override
    public void update(Course course, String id) throws Exception {
        try {
            Course existingCourse = get(id);
            courseArrayRepository.update(course, existingCourse.getCourseId());
        } catch (NotFoundException e) {
            throw new NotFoundException("Update failed because ID is not found");
        }
    }

    @Override
    public void delete(String id) {
        try {
            Course course = get(id);
            courseArrayRepository.delete(course.getCourseId());
        } catch (Exception e) {
            throw new NotFoundException("Delete failed because ID is not found");
        }
    }

    @Override
    public List<Course> getBy(String keyword, String value) throws Exception {
        Optional<List<Course>> result = courseArrayRepository.findBy(keyword, value);
        if (result.isEmpty()) {
            throw new NotFoundException("Course not found");
        }
        return result.get();
    }

    @Override
    public Page<Course> list(Integer page, Integer size, String direction, String sortBy) throws Exception {
        return null;
    }
}
