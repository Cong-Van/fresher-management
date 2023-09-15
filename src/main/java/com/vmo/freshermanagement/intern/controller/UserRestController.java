package com.vmo.freshermanagement.intern.controller;

import com.vmo.freshermanagement.intern.constant.SecurityConstant;
import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.entity.User;
import com.vmo.freshermanagement.intern.security.JwtTokenProvider;
import com.vmo.freshermanagement.intern.security.UserPrinciple;
import com.vmo.freshermanagement.intern.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserRestController {

    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    public UserRestController(UserService userService,
                              JwtTokenProvider jwtTokenProvider,
                              AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }


    // Quản lý Fresher
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        authenticate(username, password);

        User loginUser = userService.getUserByUsername(username);
        UserPrinciple userPrinciple = new UserPrinciple(loginUser);
        HttpHeaders headers = getJwtHeader(userPrinciple);

        return new ResponseEntity<>(loginUser, headers, HttpStatus.OK);
    }

    @GetMapping("/freshers")
    public List<Fresher> getAllFreshers() {
        return userService.getAllFreshers();
    }

    @GetMapping("/freshers/{fresher_id}")
    public Fresher getFresher(@PathVariable("fresher_id") int fresher_id) {
        return userService.getFresherById(fresher_id);
    }

    @PostMapping("/freshers")
    public Fresher addFresher(@Valid @RequestBody Fresher newFresher) {
        return userService.addFresher(newFresher);
    }

    @PutMapping("/freshers/{fresher_id}")
    public Fresher updateInfoFresher(@PathVariable("fresher_id") int fresherId, @RequestBody Fresher updateFresher) {
        Fresher fresher = userService.getFresherById(fresherId);
        userService.updateFresher(fresher, updateFresher);
        return fresher;
    }

    @PutMapping("/freshers/{fresher_id}/update-mark")
    public Fresher updateMark(@PathVariable("fresher_id") int fresherId,
                               @RequestParam("mark1") int mark1,
                               @RequestParam("mark2") int mark2,
                               @RequestParam("mark3") int mark3) {
        Fresher fresher = userService.getFresherById(fresherId);
        userService.updateMark1(fresher, mark1, mark2, mark3);
        return fresher;
    }

    @GetMapping("/freshers/find-by-name")
    public List<Fresher> findFresherByName(@RequestParam("name") String name) {
        return userService.getAllFresherByName(name);
    }

    @GetMapping("/freshers/find-by-language")
    public List<Fresher> findFresherByLanguage(@RequestParam("language") String language) {
        return userService.getAllFresherByLanguage(language);
    }

    @GetMapping("/freshers/find-by-email")
    public List<Fresher> findFresherByEmail(@RequestParam("email") String email) {
        return userService.getAllFresherByEmail(email);
    }

    @GetMapping("/freshers/find-by-mark")
    public List<Fresher> findFresherByMark(@RequestParam("mark") double mark) {
        return userService.getAllFresherByMark(mark);
    }

    @DeleteMapping("/freshers/{fresher_id}")
    public void deleteFresher(@PathVariable("fresher_id") int fresherId) {
        userService.deleteFresherById(fresherId);
    }



    // Quản lý Trung tâm
    @GetMapping("/centers")
    public List<Center> getAllCenter() {
        return userService.getAllCenter();
    }

    @GetMapping("/centers/{center_id}")
    public Center getCenter(@PathVariable("center_id") int centerId) {
        return userService.getCenterById(centerId);
    }

    @PostMapping("/centers")
    public Center createCenter(@RequestBody Center newCenter) {
        return userService.createCenter(newCenter);
    }

    @PutMapping("/centers/{center_id}")
    public Center updateCenter(@PathVariable("center_id") int centerId, @RequestBody Center updateCenter) {
        Center center = userService.getCenterById(centerId);
        userService.updateCenter(center, updateCenter);
        return center;
    }

    // Đưa Fresher vào trung tâm
    @PutMapping("/centers/{center_id}/{fresher_id}")
    public Fresher transferFresherToCenter(@PathVariable("center_id") int centerId,
                                                 @PathVariable("fresher_id") int fresherId) {
        Center center = userService.getCenterById(centerId);
        Fresher fresher = userService.getFresherById(fresherId);
        userService.transferFresherToCenter(fresher, center);

        return fresher;
    }

    @DeleteMapping("/centers/{center_id}")
    public void deleteCenter(@PathVariable("center_id") int centerId) {
        userService.deleteCenterById(centerId);
    }

    @GetMapping("/centers/{center_id}/freshers")
    public List<Fresher> getAllFresherOfCenter(@PathVariable("center_id") int centerId) {
        return userService.getAllFresherByCenterId(centerId);
    }



    private HttpHeaders getJwtHeader(UserPrinciple userPrinciple) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrinciple));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
