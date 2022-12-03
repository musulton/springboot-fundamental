package com.enigma.service;

import com.enigma.exception.EntityExistException;
import com.enigma.exception.NotFoundException;
import com.enigma.model.Course;
import com.enigma.model.CourseType;
import com.enigma.repository.CourseRepository;
import com.enigma.repository.CourseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("api")
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Override
    public List<Course> list() {
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    @Override
    public Course create(Course course) {
        try {
            Optional<CourseType> courseType = courseTypeRepository.findById(course.getCourseType().getCourseTypeId());

            if (courseType.isEmpty()) {
                throw new NotFoundException("Course type is not found");
            }

            course.setCourseType(courseType.get());
            Course newCourse = courseRepository.save(course);
            return newCourse;
        } catch (DataIntegrityViolationException e) {
            throw new EntityExistException();
        }
    }

    @Override
    public Course get(String id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new NotFoundException("Course not found");
        }
        return course.get();
    }

    @Override
    public void update(Course course, String id) {
        try {
            Course existingCourse = get(id);
            course.setCourseId(existingCourse.getCourseId());
            courseRepository.save(course);
        } catch (NotFoundException e) {
            throw new NotFoundException("Update failed because ID is not found");
        }
    }

    @Override
    public void delete(String id) {
        try {
            Course existingCourse = get(id);
            courseRepository.delete(existingCourse);
        } catch (NotFoundException e) {
            throw new NotFoundException("Delete failed because ID is not found");
        }
    }

    List<Course> findByTitleContains(String value) {
        List<Course> courses = courseRepository.findByTitleContains(value);
        if (courses.isEmpty()) {
            throw new NotFoundException("Course with " + value + " title is not found");
        }

        return courses;
    }

    List<Course> findByDescriptionContains(String value) {
        List<Course> courses = courseRepository.findByDescriptionContains(value);
        if (courses.isEmpty()) {
            throw new NotFoundException("Course with " + value + " description is not found");
        }

        return courses;
    }

    public List<Course> getBy(String key, String val) {
        switch (key) {
            case "title":
                return findByTitleContains(val);
            case "description":
                return findByDescriptionContains(val);
            default:
                return courseRepository.findAll();
        }
    }

    @Override
    public Page<Course> list(Integer page, Integer size, String direction, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
        Pageable pageable = PageRequest.of((page - 1), size, sort);
        Page<Course> result = courseRepository.findAll(pageable);
        return result;
    }
}
