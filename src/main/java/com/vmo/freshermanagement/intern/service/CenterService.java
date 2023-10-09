package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;

import java.util.List;

public interface CenterService {

    List<Center> getAllCenter();

    Center createCenter(Center newCenter);

    Center getCenterById(int centerId);

    Center updateCenter(Center updateCenter, String username);

    void deleteCenterById(int centerId);

    List<Fresher> getAllFresherByCenterId(int centerId);
}
