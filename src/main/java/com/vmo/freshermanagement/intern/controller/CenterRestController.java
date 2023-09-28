package com.vmo.freshermanagement.intern.controller;

import com.vmo.freshermanagement.intern.constant.SecurityConstant;
import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.entity.User;
import com.vmo.freshermanagement.intern.security.JwtTokenProvider;
import com.vmo.freshermanagement.intern.security.UserPrinciple;
import com.vmo.freshermanagement.intern.service.CenterService;
import com.vmo.freshermanagement.intern.service.FresherService;
import com.vmo.freshermanagement.intern.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CenterRestController {

    private FresherService fresherService;
    private CenterService centerService;

    public CenterRestController(FresherService fresherService,
                                CenterService centerService) {
        this.fresherService = fresherService;
        this.centerService = centerService;
    }

    @GetMapping("/centers")
    @Operation(summary = "All centers list")
    public List<Center> getAllCenter() {
        return centerService.getAllCenter();
    }

    @GetMapping("/centers/{center_id}")
    @Operation(summary = "Each center information")
    public Center getCenter(@PathVariable("center_id") int centerId) {
        return centerService.getCenterById(centerId);
    }

    @PostMapping("/centers")
    @Operation(summary = "Create center information")
    public Center createCenter(@RequestBody Center newCenter) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        newCenter.setCreatedBy(username);
        return centerService.createCenter(newCenter);
    }

    @PutMapping("/centers/{center_id}")
    @Operation(summary = "Update center information")
    public Center updateCenter(@PathVariable("center_id") int centerId, @RequestBody Center updateCenter) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Center center = centerService.getCenterById(centerId);

        center.setUpdatedBy(username);
        centerService.updateCenter(center, updateCenter);
        return center;
    }

    @PutMapping("/centers/{center_id}/{fresher_id}")
    @Operation(summary = "Add fresher to center")
    public Fresher transferFresherToCenter(@PathVariable("center_id") int centerId,
                                           @PathVariable("fresher_id") int fresherId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Center center = centerService.getCenterById(centerId);
        Fresher fresher = fresherService.getFresherById(fresherId);

        center.setUpdatedBy(username);
        centerService.transferFresherToCenter(fresher, center);
        return fresher;
    }

    @DeleteMapping("/centers/{center_id}")
    @Operation(summary = "Delete center information")
    public void deleteCenter(@PathVariable("center_id") int centerId) {
        centerService.deleteCenterById(centerId);
    }

    @GetMapping("/centers/{center_id}/freshers")
    @Operation(summary = "Freshers list of Center")
    public List<Fresher> getAllFresherOfCenter(@PathVariable("center_id") int centerId) {
        return centerService.getAllFresherByCenterId(centerId);
    }
}
