package com.vmo.freshermanagement.intern.service.impl;

import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.exception.FresherNotFoundException;
import com.vmo.freshermanagement.intern.repository.FresherRepository;
import com.vmo.freshermanagement.intern.service.FresherService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.vmo.freshermanagement.intern.constant.ExceptionConstant.NOT_FOUND_FRESHER;

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
        newFresher.setJoinedDate(LocalDate.now());
        return fresherRepository.save(newFresher);
    }

    @Override
    public void updateFresher(Fresher fresher, Fresher updateFresher) {
        fresher.setCenter(updateFresher.getCenter());
        fresher.setDob(updateFresher.getDob());
        fresher.setEmail(updateFresher.getEmail());
        fresher.setGender(updateFresher.getGender());
        fresher.setLanguage(updateFresher.getLanguage());
        fresher.setName(updateFresher.getName());
        fresher.setPhone(updateFresher.getPhone());
        fresher.setPosition(updateFresher.getPosition());
        fresherRepository.save(fresher);
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
}
