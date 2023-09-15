package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);

    List<Fresher> getAllFreshers();

    Fresher addFresher(Fresher newFresher);

    Fresher getFresherById(int fresherId);

    void updateFresher(Fresher fresher, Fresher updateFresher);

    void deleteFresherById(int fresherId);

    void updateMark1(Fresher fresher, int mark1, int mark2, int mark3);


    List<Fresher> getAllFresherByName(String name);

    List<Fresher> getAllFresherByLanguage(String language);

    List<Fresher> getAllFresherByEmail(String email);

    List<Center> getAllCenter();

    Center createCenter(Center newCenter);

    Center getCenterById(int centerId);

    void updateCenter(Center center, Center updateCenter);

    void deleteCenterById(int centerId);

    void transferFresherToCenter(Fresher fresher, Center center);

    List<Fresher> getAllFresherByCenterId(int centerId);

    List<Fresher> getAllFresherByMark(double mark);
}
