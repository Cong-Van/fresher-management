package com.vmo.freshermanagement.intern.repository;

import com.vmo.freshermanagement.intern.entity.Fresher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FresherRepository extends JpaRepository<Fresher, Integer> {

    Fresher findByEmail(String email);

    Fresher findByPhone(String phone);

    List<Fresher> findAllByCenterId(int centerId);

    @Query(value = "SELECT f FROM Fresher f WHERE name LIKE %?1%")
    List<Fresher> findAllByName(String name);

    List<Fresher> findAllByLanguage(String language);

    @Query(value = "SELECT f FROM Fresher f WHERE email LIKE %?1%")
    List<Fresher> findAllByEmail(String email);

    List<Fresher> findAllByMarkAvg(double mark);

    @Query(value = "SELECT f FROM Fresher f WHERE graduatedDate is null")
    List<Fresher> findAllNotGraduatedFresher();

    @Query(value = "SELECT f FROM Fresher f WHERE ?1 <= markAvg and markAvg <= ?2")
    List<Fresher> findAllByMarkRange(double mark1, double mark2);
}
