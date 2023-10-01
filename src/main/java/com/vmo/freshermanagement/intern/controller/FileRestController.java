package com.vmo.freshermanagement.intern.controller;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class FileRestController {

    private FileService fileService;

    public FileRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/freshers/upload-by-file")
    @Operation(summary = "Bulk add users by file")
    public List<Fresher> addFresherByFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.addFresherByFile(file);
    }

    @PostMapping("/centers/upload-by-file")
    @Operation(summary = "Bulk add centers by file")
    public List<Center> addCenterByFile(@RequestParam("file") MultipartFile file) throws IOException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return fileService.addCenterByFile(file, username);
    }
}
