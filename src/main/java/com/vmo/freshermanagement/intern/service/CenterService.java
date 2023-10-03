package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;

import java.util.List;

public interface CenterService {

    List<Center> getAllCenter();

    Center createCenter(Center newCenter);

    Center getCenterById(int centerId);

    Center updateCenter(int centerId, String username, String name, String phone, String address, String description);

    void deleteCenterById(int centerId);

    Fresher transferFresherToCenter(int centerId, int fresherId, String username);

    List<Fresher> getAllFresherByCenterId(int centerId);
}
