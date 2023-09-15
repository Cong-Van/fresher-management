package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.entity.User;
import com.vmo.freshermanagement.intern.exception.CenterNotFoundException;
import com.vmo.freshermanagement.intern.exception.FresherNotFoundException;
import com.vmo.freshermanagement.intern.repository.CenterRepository;
import com.vmo.freshermanagement.intern.repository.FresherRepository;
import com.vmo.freshermanagement.intern.repository.UserRepository;
import com.vmo.freshermanagement.intern.security.UserPrinciple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.vmo.freshermanagement.intern.constant.ExceptionConstant.NOT_FOUND_FRESHER;
import static com.vmo.freshermanagement.intern.constant.UserServiceConstant.FOUND_USER_BY_USERNAME;
import static com.vmo.freshermanagement.intern.constant.UserServiceConstant.USERNAME_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {

    public static final String NOT_FOUND_CENTER = "Not found Center";
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;
    private CenterRepository centerRepository;
    private FresherRepository fresherRepository;

    public UserServiceImpl(UserRepository userRepository,
                           CenterRepository centerRepository,
                           FresherRepository fresherRepository) {
        this.userRepository = userRepository;
        this.centerRepository = centerRepository;
        this.fresherRepository = fresherRepository;
    }



    // Fresher Management
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOGGER.error(String.format(USERNAME_NOT_FOUND, username));
            throw new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, username));
        } else {
            LOGGER.info(String.format(FOUND_USER_BY_USERNAME, username));
            return new UserPrinciple(user);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, username));
        }
        return user;
    }

    @Override
    public List<Fresher> getAllFreshers() {
        return fresherRepository.findAll();
    }

    @Override
    public Fresher addFresher(Fresher newFresher) {
        return fresherRepository.save(newFresher);
    }

    @Override
    public Fresher getFresherById(int fresherId) {
        Optional<Fresher> fresher = fresherRepository.findById(fresherId);
        if (!fresher.isPresent()) {
            throw new FresherNotFoundException(NOT_FOUND_FRESHER);
        }
        return fresher.get();
    }

    @Override
    public void updateFresher(Fresher fresher, Fresher updateFresher) {
        fresher.setCenter(updateFresher.getCenter());
        fresher.setDob(updateFresher.getDob());
        fresher.setEmail(updateFresher.getEmail());
        fresher.setGender(updateFresher.getGender());
        fresher.setLanguage(updateFresher.getLanguage());
        fresher.setName(updateFresher.getName());
        fresher.setPhone(updateFresher.getPhone());
        fresher.setPosition(updateFresher.getPosition());
        fresherRepository.save(fresher);
    }

    @Override
    public void updateMark1(Fresher fresher, int mark1, int mark2, int mark3) {
        fresher.setMark1(mark1);
        fresher.setMark2(mark2);
        fresher.setMark3(mark3);
        fresher.setMarkAvg();
        fresherRepository.save(fresher);
    }

    @Override
    public List<Fresher> getAllFresherByName(String name) {
        return fresherRepository.findAllByName(name);
    }

    @Override
    public List<Fresher> getAllFresherByLanguage(String language) {
        return fresherRepository.findAllByLanguage(language);
    }

    @Override
    public List<Fresher> getAllFresherByEmail(String email) {
        return fresherRepository.findAllByEmail(email);
    }

    @Override
    public List<Fresher> getAllFresherByMark(double mark) {
        return fresherRepository.findAllByMark(mark);
    }

    @Override
    public void deleteFresherById(int fresherId) {
        Optional<Fresher> fresher = fresherRepository.findById(fresherId);
        if (!fresher.isPresent()) {
            throw new FresherNotFoundException(NOT_FOUND_FRESHER);
        }
        fresherRepository.delete(fresher.get());
    }



    // Center Management
    @Override
    public List<Center> getAllCenter() {
        return centerRepository.findAll();
    }

    @Override
    public Center createCenter(Center newCenter) {
        centerRepository.save(newCenter);
        return newCenter;
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
    public void updateCenter(Center center, Center updateCenter) {
        center.setName(updateCenter.getName());
        center.setPhone(updateCenter.getPhone());
        center.setAddress(updateCenter.getAddress());
        center.setDescription(updateCenter.getDescription());
        centerRepository.save(center);
    }

    @Override
    public void transferFresherToCenter(Fresher fresher, Center center) {
        fresher.setCenter(center);
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
}
