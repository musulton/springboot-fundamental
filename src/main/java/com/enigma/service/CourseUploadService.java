package com.enigma.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface CourseUploadService {
    String uploadMaterial(MultipartFile multipartFile);

    Resource downloadMaterial(String filename);
}
