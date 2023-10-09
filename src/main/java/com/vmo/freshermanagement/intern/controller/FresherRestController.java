package com.vmo.freshermanagement.intern.controller;

import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.service.FresherService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class FresherRestController {

    private FresherService fresherService;

    public FresherRestController(FresherService fresherService) {
        this.fresherService = fresherService;
    }

    @GetMapping("/freshers")
    @Cacheable(value = "freshers", key = "'allFreshers'")
    @Operation(summary = "All freshers list")
    public List<Fresher> getAllFreshers() {
        System.out.println("Thử");
        return fresherService.getAllFreshers();
    }

    @GetMapping("/freshers/{fresher_id}")
    @Operation(summary = "Each fresher information")
    public Fresher getFresher(@PathVariable("fresher_id") int fresherId) {
        return fresherService.getFresherById(fresherId);
    }

    @PostMapping("/freshers")
    @Operation(summary = "Add new fresher")
    public Fresher addFresher(@Valid @RequestBody Fresher newFresher) {
        return fresherService.addFresher(newFresher);
    }

    @PutMapping("/freshers")
    @Operation(summary = "Update fresher information")
    public Fresher updateInfoFresher(@RequestBody Fresher updateFresher) {

        return fresherService.updateFresher(updateFresher);
    }

    @DeleteMapping("/freshers/{fresher_id}")
    @CacheEvict(value = "freshers", allEntries = true)
    @Operation(summary = "Remove fresher")
    public void deleteFresher(@PathVariable("fresher_id") int fresherId) {
        fresherService.deleteFresherById(fresherId);
    }

    @PutMapping("/freshers/update-mark/{fresher_id}")
    @Operation(summary = "Update fresher's mark")
    public Fresher updateMark(@PathVariable("fresher_id") int fresherId,
                              @RequestParam("mark1") double mark1,
                              @RequestParam("mark2") double mark2,
                              @RequestParam("mark3") double mark3) {
        Fresher fresher = fresherService.getFresherById(fresherId);
        fresherService.updateMark(fresher, mark1, mark2, mark3);
        return fresher;
    }

    @GetMapping("/freshers/find-by-name")
    @Operation(summary = "Find freshers by name")
    public List<Fresher> findFresherByName(@RequestParam("name") String name) {
        return fresherService.getAllFresherByName(name);
    }

    @GetMapping("/freshers/find-by-language")
    @Operation(summary = "Find freshers by language")
    public List<Fresher> findFresherByLanguage(@RequestParam("language") String language) {
        return fresherService.getAllFresherByLanguage(language);
    }

    @GetMapping("/freshers/find-by-email")
    @Operation(summary = "Find freshers by email")
    public List<Fresher> findFresherByEmail(@RequestParam("email") String email) {
        return fresherService.getAllFresherByEmail(email);
    }

    @GetMapping("/freshers/find-by-mark")
    @Operation(summary = "Find freshers by mark")
    public List<Fresher> findFresherByMark(@RequestParam("mark") double mark) {
        return fresherService.getAllFresherByMark(mark);
    }

    @GetMapping("/freshers/find-by-mark-range")
    @Operation(summary = "Find freshers by mark (in mark range)")
    public List<Fresher> findFresherByMarkRange(@RequestParam("mark1") double mark1,
                                                @RequestParam("mark2") double mark2) {
        return fresherService.getAllFresherByMark(mark1, mark2);
    }

    @PutMapping("/freshers/transfer-to-center/{fresher_id}")
    @Operation(summary = "Add fresher to center")
    public Fresher transferFresherToCenter(@PathVariable("fresher_id") int fresherId,
                                           @RequestBody Center center) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return fresherService.transferFresherToCenter(fresherId, center, username);
    }

    @PutMapping("/freshers/graduate/{fresher_id}")
    @Operation(summary = "Update fresher status to graduate")
    public Fresher updateGraduatedFresherStatus(@PathVariable("fresher_id") int fresherId) {
        return fresherService.updateGraduatedFresherStatus(fresherId);
    }
}
