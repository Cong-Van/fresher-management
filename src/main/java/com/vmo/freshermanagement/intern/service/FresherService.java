package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.entity.Fresher;

import java.util.List;

public interface FresherService {

    List<Fresher> getAllFreshers();

    Fresher getFresherById(int fresherId);

    Fresher addFresher(Fresher newFresher);

    Fresher updateFresher(int fresherId, String name, String dob, String gender,
                          String phone, String email, String position, String language);

    void deleteFresherById(int fresherId);

    void updateMark(Fresher fresher, double mark1, double mark2, double mark3);

    List<Fresher> getAllFresherByName(String name);

    List<Fresher> getAllFresherByLanguage(String language);

    List<Fresher> getAllFresherByEmail(String email);

    List<Fresher> getAllFresherByMark(double mark);

    List<Fresher> getAllFresherByMark(double mark1, double mark2);

    Fresher updateGraduatedFresherStatus(int fresherId);
}
