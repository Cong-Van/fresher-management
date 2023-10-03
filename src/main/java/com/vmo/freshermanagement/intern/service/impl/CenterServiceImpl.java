package com.vmo.freshermanagement.intern.service.impl;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.exception.CenterNotFoundException;
import com.vmo.freshermanagement.intern.exception.FresherNotFoundException;
import com.vmo.freshermanagement.intern.repository.CenterRepository;
import com.vmo.freshermanagement.intern.repository.FresherRepository;
import com.vmo.freshermanagement.intern.service.CenterService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.vmo.freshermanagement.intern.constant.ExceptionConstant.NOT_FOUND_CENTER;
import static com.vmo.freshermanagement.intern.constant.ExceptionConstant.NOT_FOUND_FRESHER;

@Service
public class CenterServiceImpl implements CenterService {

    private FresherRepository fresherRepository;
    private CenterRepository centerRepository;

    public CenterServiceImpl(FresherRepository fresherRepository,
                             CenterRepository centerRepository) {
        this.fresherRepository = fresherRepository;
        this.centerRepository = centerRepository;
    }

    @Override
    public List<Center> getAllCenter() {
        return centerRepository.findAll();
    }

    @Override
    public Center getCenterById(int centerId) {
        Optional<Center> center = centerRepository.findById(centerId);
        if (center.isEmpty()) {
            throw new CenterNotFoundException(NOT_FOUND_CENTER);
        }
        return center.get();
    }

    @Override
    public Center createCenter(Center newCenter) {
        newCenter.setCreatedDate(LocalDateTime.now());
        centerRepository.save(newCenter);
        return newCenter;
    }

    @Override
    public Center updateCenter(int centerId, String username, String name, String phone, String address, String description) {
        Center center = getCenterById(centerId);

        center.setUpdatedBy(username);
        center.setName(name);
        center.setPhone(phone);
        center.setAddress(address);
        center.setDescription(description);
        center.setUpdatedDate(LocalDateTime.now());

        return centerRepository.save(center);
    }

    @Override
    public void deleteCenterById(int centerId) {
        Optional<Center> center = centerRepository.findById(centerId);
        if (center.isEmpty()) {
            throw new CenterNotFoundException(NOT_FOUND_CENTER);
        }
        List<Fresher> fresherList = fresherRepository.findAllByCenterId(centerId);
        for (Fresher fresher : fresherList) {
            fresher.setCenter(null);
            fresherRepository.save(fresher);
        }
        centerRepository.deleteById(centerId);
    }

    @Override
    public Fresher transferFresherToCenter(int centerId, int fresherId, String username) {
        Center center = getCenterById(centerId);
        Optional<Fresher> fresherOp = fresherRepository.findById(fresherId);
        if (fresherOp.isEmpty()) {
            throw new FresherNotFoundException(NOT_FOUND_FRESHER);
        }
        Fresher fresher = fresherOp.get();

        fresher.setCenter(center);
        center.setUpdatedBy(username);
        center.setUpdatedDate(LocalDateTime.now());
        return fresherRepository.save(fresher);
    }

    @Override
    public List<Fresher> getAllFresherByCenterId(int centerId) {
        Optional<Center> center = centerRepository.findById(centerId);
        if (center.isEmpty()) {
            throw new CenterNotFoundException(NOT_FOUND_CENTER);
        }
        return fresherRepository.findAllByCenterId(centerId);
    }
}
