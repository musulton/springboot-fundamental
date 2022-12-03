package com.enigma.service;

import com.enigma.model.CourseType;
import org.springframework.data.domain.Page;

public interface CourseTypeService {
    Page<CourseType> list(Integer page, Integer size, String sortBy, String direction);
    CourseType create(CourseType courseType);
}
