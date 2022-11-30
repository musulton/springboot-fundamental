package com.enigma.controller;

import com.enigma.model.Course;
import com.enigma.model.request.CourseRequest;
import com.enigma.model.response.SuccessResponse;
import com.enigma.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/courses")
@Validated
public class CourseController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity getAllCourse() throws Exception {
        List<Course> courses = courseService.list();
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success Get All Course", courses));
    }

    @PostMapping
    public ResponseEntity createCourse(@Valid @RequestBody CourseRequest course) throws Exception {
        Course newCourse = modelMapper.map(course, Course.class);
        Course result = courseService.create(newCourse);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("Success create course", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") String id) throws Exception {
        Course course = courseService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success get course with id", course));
    }

    @GetMapping(params = {"keyword", "value"})
    public ResponseEntity getBy(@RequestParam @NotBlank(message = "{invalid.keyword.required}") String keyword, @RequestParam String value) throws Exception {
        List<Course> result = courseService.getBy(keyword, value);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success get course by " + keyword, result));
        }

    @PutMapping("/{id}")
    public ResponseEntity updateById(@Valid @RequestBody CourseRequest courseRequest, @PathVariable("id") String id) throws Exception {
        Course course = modelMapper.map(courseRequest, Course.class);
        courseService.update(course, id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success update course", course));
    }
}
