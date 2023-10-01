package com.vmo.freshermanagement.intern.service.impl;

import com.vmo.freshermanagement.intern.common.Gender;
import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.repository.FresherRepository;
import com.vmo.freshermanagement.intern.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.vmo.freshermanagement.intern.constant.ServiceConstant.COMMA_DELIMITER;
import static com.vmo.freshermanagement.intern.constant.ServiceConstant.DATE_FORMAT;

@Service
public class FileServiceImpl implements FileService {

    private FresherRepository fresherRepository;

    public FileServiceImpl(FresherRepository fresherRepository) {
        this.fresherRepository = fresherRepository;
    }

    @Override
    public List<Fresher> addFresherByFile(MultipartFile file) throws IOException {
        List<Fresher> freshers = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            freshers = br.lines()
                    .map(line -> {
                        String[] parts = line.split(COMMA_DELIMITER);
                        if (parts.length == 7) {
                            Fresher newFresher = new Fresher();
                            newFresher.setName(parts[0].trim());
                            newFresher.setDob(dateValue(parts[1].trim()));
                            newFresher.setGender(genderValue(parts[2].trim()));
                            newFresher.setPhone(parts[3].trim());
                            newFresher.setEmail(parts[4].trim());
                            newFresher.setPosition(parts[5].trim());
                            newFresher.setLanguage(parts[6].trim());
                            newFresher.setJoinedDate(LocalDate.now());
                            return newFresher;
                        } else {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        fresherRepository.saveAll(freshers);
        return freshers;
    }

    @Override
    public List<Center> addCenterByFile(MultipartFile file, String username) throws IOException {
        List<Center> centers = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            centers = br.lines()
                    .map(line -> {
                        String[] parts = line.split(COMMA_DELIMITER);
                        if (parts.length == 4) {
                            Center newCenter = new Center();
                            newCenter.setName(parts[0].trim());
                            newCenter.setAddress(parts[1].trim());
                            newCenter.setPhone(parts[2].trim());
                            newCenter.setDescription(parts[3].trim());
                            newCenter.setCreatedDate(LocalDateTime.now());
                            newCenter.setCreatedBy(username);
                            return newCenter;
                        } else {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return centers;
    }

    private LocalDate dateValue(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(date, dtf);
    }

    private Gender genderValue(String name) {
        return Gender.valueOf(name);
    }
}
