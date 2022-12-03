package com.enigma.service;

import com.enigma.exception.EntityExistException;
import com.enigma.model.CourseType;
import com.enigma.repository.CourseTypeRepository;
import com.enigma.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Profile("api")
public class CourseTypeServiceImpl implements CourseTypeService {
    @Autowired
    CourseTypeRepository courseTypeRepository;

    @Autowired
    RandomStringGenerator randomStringGenerator;

    @Override
    public Page<CourseType> list(Integer page, Integer size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
        Pageable pageable = PageRequest.of((page - 1), size, sort);
        Page<CourseType> courses = courseTypeRepository.findAll(pageable);
        return courses;
    }

    @Override
    public CourseType create(CourseType courseType) {
        try {
            courseType.setCourseTypeId(randomStringGenerator.random());
            CourseType newCourseType = courseTypeRepository.save(courseType);
            return newCourseType;
        } catch (DataIntegrityViolationException e) {
            throw new EntityExistException();
        }
    }
}
