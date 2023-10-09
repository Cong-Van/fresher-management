package com.vmo.freshermanagement.intern.service.impl;

import com.vmo.freshermanagement.intern.common.Gender;
import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.exception.DataAlreadyExistException;
import com.vmo.freshermanagement.intern.exception.DataNotFoundException;
import com.vmo.freshermanagement.intern.repository.CenterRepository;
import com.vmo.freshermanagement.intern.repository.FresherRepository;
import com.vmo.freshermanagement.intern.service.FresherService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.vmo.freshermanagement.intern.constant.ExceptionConstant.*;
import static com.vmo.freshermanagement.intern.constant.ServiceConstant.DATE_FORMAT;

@Service
public class FresherServiceImpl implements FresherService {

    private FresherRepository fresherRepository;
    private CenterRepository centerRepository;

    public FresherServiceImpl(FresherRepository fresherRepository, CenterRepository centerRepository) {
        this.fresherRepository = fresherRepository;
        this.centerRepository = centerRepository;
    }

    @Override
    public List<Fresher> getAllFreshers() {
        return fresherRepository.findAll();
    }

    @Override
    public Fresher getFresherById(int fresherId) {
        Optional<Fresher> fresher = fresherRepository.findById(fresherId);
        if (fresher.isEmpty()) {
            throw new DataNotFoundException(NOT_FOUND_FRESHER);
        }
        return fresher.get();
    }

    @Override
    public Fresher addFresher(Fresher newFresher) {
        String email = newFresher.getEmail();
        String phone = newFresher.getPhone();

        Fresher savedFresher = fresherRepository.findByEmail(email);
        if (savedFresher != null) {
            throw new DataAlreadyExistException(String.format(EMAIL_WAS_IN_USE, email, savedFresher.getName()));
        }
        savedFresher = fresherRepository.findByPhone(phone);
        if (savedFresher != null) {
            throw new DataAlreadyExistException(String.format(PHONE_NUMBER_WAS_IN_USE, phone, savedFresher.getName()));
        }

        newFresher.setJoinedDate(LocalDate.now());
        return fresherRepository.save(newFresher);
    }

    @Override
    public Fresher updateFresher(Fresher updateFresher) {
        String email = updateFresher.getEmail();
        String phone = updateFresher.getPhone();

        Fresher savedFresher = fresherRepository.findByEmail(email);
        if (savedFresher != null && updateFresher.getId() != savedFresher.getId()) {
            throw new DataAlreadyExistException(String.format(EMAIL_WAS_IN_USE, email, savedFresher.getName()));
        }
        savedFresher = fresherRepository.findByPhone(updateFresher.getPhone());
        if (savedFresher != null && updateFresher.getId() != savedFresher.getId()) {
            throw new DataAlreadyExistException(String.format(PHONE_NUMBER_WAS_IN_USE, phone, savedFresher.getName()));
        }

        return fresherRepository.save(updateFresher);
    }

    @Override
    public void updateMark(Fresher fresher, double mark1, double mark2, double mark3) {
        fresher.setMark1(mark1);
        fresher.setMark2(mark2);
        fresher.setMark3(mark3);
        fresher.setMarkAvg();
        fresherRepository.save(fresher);
    }

    @Override
    public void deleteFresherById(int fresherId) {
        Optional<Fresher> fresher = fresherRepository.findById(fresherId);
        if (fresher.isEmpty()) {
            throw new DataNotFoundException(NOT_FOUND_FRESHER);
        }
        fresherRepository.delete(fresher.get());
    }

    @Override
    public List<Fresher> getAllFresherByName(String name) {
        return fresherRepository.findAllByName(name);
    }

    @Override
    public List<Fresher> getAllFresherByLanguage(String language) {
        return fresherRepository.findAllByLanguage(language);
    }

    @Override
    public List<Fresher> getAllFresherByEmail(String email) {
        return fresherRepository.findAllByEmail(email);
    }

    @Override
    public List<Fresher> getAllFresherByMark(double mark) {
        return fresherRepository.findAllByMarkAvg(mark);
    }

    @Override
    public List<Fresher> getAllFresherByMark(double mark1, double mark2) {
        return fresherRepository.findAllByMarkRange(mark1, mark2);
    }

    @Override
    public Fresher transferFresherToCenter(int fresherId, Center center, String username) {
        Fresher fresher = getFresherById(fresherId);

        fresher.setCenter(center);
        center.setUpdatedBy(username);
        center.setUpdatedDate(LocalDateTime.now());
        return fresherRepository.save(fresher);
    }

    @Override
    public Fresher updateGraduatedFresherStatus(int fresherId) {
        Fresher fresher = getFresherById(fresherId);
        fresher.setGraduatedDate(LocalDate.now());
        return fresher;
    }

    private LocalDate dateValue(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(date, dtf);
    }

    private Gender genderValue(String name) {
        return Gender.valueOf(name);
    }
}
