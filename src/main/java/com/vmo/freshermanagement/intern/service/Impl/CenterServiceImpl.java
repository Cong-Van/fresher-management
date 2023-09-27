package com.vmo.freshermanagement.intern.service.Impl;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.exception.CenterNotFoundException;
import com.vmo.freshermanagement.intern.repository.CenterRepository;
import com.vmo.freshermanagement.intern.repository.FresherRepository;
import com.vmo.freshermanagement.intern.service.CenterService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.vmo.freshermanagement.intern.constant.ExceptionConstant.NOT_FOUND_CENTER;

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
        if (!center.isPresent()) {
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
    public void updateCenter(Center center, Center updateCenter) {
        center.setName(updateCenter.getName());
        center.setPhone(updateCenter.getPhone());
        center.setAddress(updateCenter.getAddress());
        center.setDescription(updateCenter.getDescription());
        center.setUpdatedDate(LocalDateTime.now());
        centerRepository.save(center);
    }

    @Override
    public void deleteCenterById(int centerId) {
        Optional<Center> center = centerRepository.findById(centerId);
        if (!center.isPresent()) {
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
    public void transferFresherToCenter(Fresher fresher, Center center) {
        fresher.setCenter(center);
        center.setUpdatedDate(LocalDateTime.now());
        fresherRepository.save(fresher);
    }

    @Override
    public List<Fresher> getAllFresherByCenterId(int centerId) {
        Optional<Center> center = centerRepository.findById(centerId);
        if (!center.isPresent()) {
            throw new CenterNotFoundException(NOT_FOUND_CENTER);
        }
        return fresherRepository.findAllByCenterId(centerId);
    }
}
