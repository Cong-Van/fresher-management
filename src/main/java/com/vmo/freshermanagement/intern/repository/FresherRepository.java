package com.vmo.freshermanagement.intern.repository;

import com.vmo.freshermanagement.intern.entity.Fresher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FresherRepository extends JpaRepository<Fresher, Integer> {

    Fresher findByEmail(String email);

    Fresher findByPhone(String phone);

    List<Fresher> findAllByCenterId(int centerId);

    @Query(value = "SELECT * FROM freshers WHERE name LIKE %?1%", nativeQuery = true)
    List<Fresher> findAllByName(String name);

    List<Fresher> findAllByLanguage(String language);

    @Query(value = "SELECT * FROM freshers WHERE email LIKE %?1%", nativeQuery = true)
    List<Fresher> findAllByEmail(String email);

    List<Fresher> findAllByMarkAvg(double mark);

    @Query(value = "SELECT * FROM freshers WHERE graduated_date is null", nativeQuery = true)
    List<Fresher> findAllNotGraduatedFresher();
}
