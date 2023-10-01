package com.vmo.freshermanagement.intern.service.impl;

import com.vmo.freshermanagement.intern.common.Gender;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.exception.EmailExistException;
import com.vmo.freshermanagement.intern.exception.FresherNotFoundException;
import com.vmo.freshermanagement.intern.exception.PhoneExistException;
import com.vmo.freshermanagement.intern.repository.FresherRepository;
import com.vmo.freshermanagement.intern.service.FresherService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.vmo.freshermanagement.intern.constant.ExceptionConstant.*;
import static com.vmo.freshermanagement.intern.constant.ServiceConstant.DATE_FORMAT;

@Service
public class FresherServiceImpl implements FresherService {

    private FresherRepository fresherRepository;

    public FresherServiceImpl(FresherRepository fresherRepository) {
        this.fresherRepository = fresherRepository;
    }

    @Override
    public List<Fresher> getAllFreshers() {
        return fresherRepository.findAll();
    }

    @Override
    public Fresher getFresherById(int fresherId) {
        Optional<Fresher> fresher = fresherRepository.findById(fresherId);
        if (!fresher.isPresent()) {
            throw new FresherNotFoundException(NOT_FOUND_FRESHER);
        }
        return fresher.get();
    }

    @Override
    public Fresher addFresher(Fresher newFresher) {
        String email = newFresher.getEmail();
        String phone = newFresher.getPhone();

        Fresher savedFresher = fresherRepository.findByEmail(email);
        if (savedFresher != null) {
            throw new EmailExistException(String.format(EMAIL_WAS_IN_USE, email, savedFresher.getName()));
        }
        savedFresher = fresherRepository.findByPhone(phone);
        if (savedFresher != null) {
            throw new PhoneExistException(String.format(PHONE_NUMBER_WAS_IN_USE, phone, savedFresher.getName()));
        }

        newFresher.setJoinedDate(LocalDate.now());
        return fresherRepository.save(newFresher);
    }

    @Override
    public Fresher updateFresher(int fresherId, String name, String dob, String gender,
                                 String phone, String email, String position, String language) {
        Optional<Fresher> fresherOp = fresherRepository.findById(fresherId);
        if (!fresherOp.isPresent()) {
            throw new FresherNotFoundException(NOT_FOUND_FRESHER);
        }
        Fresher fresher = fresherOp.get();

        Fresher savedFresher = fresherRepository.findByEmail(email);
        if (savedFresher != null && !fresher.getEmail().equals(email)) {
            throw new EmailExistException(String.format(EMAIL_WAS_IN_USE, email, savedFresher.getName()));
        }
        savedFresher = fresherRepository.findByPhone(phone);
        if (savedFresher != null && !fresher.getPhone().equals(phone)) {
            throw new PhoneExistException(String.format(PHONE_NUMBER_WAS_IN_USE, phone, savedFresher.getName()));
        }

        fresher.setName(name);
        fresher.setDob(dateValue(dob));
        fresher.setGender(genderValue(gender));
        fresher.setPhone(phone);
        fresher.setEmail(email);
        fresher.setPosition(position);
        fresher.setLanguage(language);
        return fresherRepository.save(fresher);
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
        if (!fresher.isPresent()) {
            throw new FresherNotFoundException(NOT_FOUND_FRESHER);
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

    private LocalDate dateValue(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(date, dtf);
    }

    private Gender genderValue(String name) {
        return Gender.valueOf(name);
    }
}
