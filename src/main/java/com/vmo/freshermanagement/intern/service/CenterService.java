package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;

import java.util.List;

public interface CenterService {

    List<Center> getAllCenter();

    Center createCenter(Center newCenter);

    Center getCenterById(int centerId);

    void updateCenter(Center center, Center updateCenter);

    void deleteCenterById(int centerId);

    void transferFresherToCenter(Fresher fresher, Center center);

    List<Fresher> getAllFresherByCenterId(int centerId);
}
