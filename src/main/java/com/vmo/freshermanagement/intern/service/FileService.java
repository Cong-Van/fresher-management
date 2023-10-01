package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<Fresher> addFresherByFile(MultipartFile file) throws IOException;

    List<Center> addCenterByFile(MultipartFile file, String username) throws IOException;

}
