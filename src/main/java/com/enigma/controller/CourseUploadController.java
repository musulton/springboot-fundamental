package com.enigma.controller;

import com.enigma.model.request.FormDataWithFile;
import com.enigma.model.response.SuccessResponse;
import com.enigma.service.CourseUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/course-files")
public class CourseUploadController {
    private CourseUploadService courseUploadService;

    @Autowired
    public CourseUploadController(CourseUploadService courseUploadService) {
        this.courseUploadService = courseUploadService;
    }

    @PostMapping
    public ResponseEntity upload(FormDataWithFile formDataWithFile) {
        MultipartFile file = formDataWithFile.getFile();
        courseUploadService.uploadMaterial(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("Success upload course", file.getOriginalFilename()));
    }

    @GetMapping
    public ResponseEntity download(@RequestParam String filename) {
        Resource file = courseUploadService.downloadMaterial(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/images/{name}")
    public ResponseEntity show(@PathVariable("name") String filename) throws IOException {
        Resource file = courseUploadService.downloadMaterial(filename);
        byte[] imageBytes = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(imageBytes);
    }
}
