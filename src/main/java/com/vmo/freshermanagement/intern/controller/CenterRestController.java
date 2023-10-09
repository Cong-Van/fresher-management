package com.vmo.freshermanagement.intern.controller;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.service.CenterService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CenterRestController {

    private CenterService centerService;

    public CenterRestController(CenterService centerService) {
        this.centerService = centerService;
    }

    @GetMapping("/centers")
    @Cacheable(value = "centers", key = "allCenters")
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
    public Center createCenter(@Valid @RequestBody Center newCenter) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        newCenter.setCreatedBy(username);
        return centerService.createCenter(newCenter);
    }

    @PutMapping("/centers")
    @Operation(summary = "Update center information")
    public Center updateCenter(@Valid @RequestBody Center updateCenter) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return centerService.updateCenter(updateCenter, username);
    }

    @DeleteMapping("/centers/{center_id}")
    @CacheEvict(value = "centers", allEntries = true)
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
