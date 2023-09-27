package com.vmo.freshermanagement.intern.repository;

import com.vmo.freshermanagement.intern.entity.Fresher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FresherRepository extends JpaRepository<Fresher, Integer> {

    List<Fresher> findAllByCenterId(int centerId);

    @Query(value = "SELECT * FROM freshers WHERE name LIKE %?1%", nativeQuery = true)
    List<Fresher> findAllByName(String name);

    List<Fresher> findAllByLanguage(String language);

    @Query(value = "SELECT * FROM freshers WHERE email LIKE %?1%", nativeQuery = true)
    List<Fresher> findAllByEmail(String email);

    List<Fresher> findAllByMarkAvg(double mark);
}
