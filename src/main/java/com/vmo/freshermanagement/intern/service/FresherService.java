package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;

import java.util.List;

public interface FresherService {

    List<Fresher> getAllFreshers();

    Fresher getFresherById(int fresherId);

    Fresher addFresher(Fresher newFresher);

    Fresher updateFresher(Fresher updateFresher);

    void deleteFresherById(int fresherId);

    void updateMark(Fresher fresher, double mark1, double mark2, double mark3);

    List<Fresher> getAllFresherByName(String name);

    List<Fresher> getAllFresherByLanguage(String language);

    List<Fresher> getAllFresherByEmail(String email);

    List<Fresher> getAllFresherByMark(double mark);

    List<Fresher> getAllFresherByMark(double mark1, double mark2);

    Fresher transferFresherToCenter(int fresherId, Center center, String username);

    Fresher updateGraduatedFresherStatus(int fresherId);
}
