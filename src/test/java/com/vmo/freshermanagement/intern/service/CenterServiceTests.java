package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.common.Gender;
import com.vmo.freshermanagement.intern.entity.Center;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.exception.CenterNotFoundException;
import com.vmo.freshermanagement.intern.repository.CenterRepository;
import com.vmo.freshermanagement.intern.repository.FresherRepository;
import com.vmo.freshermanagement.intern.service.impl.CenterServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.vmo.freshermanagement.intern.constant.ServiceConstant.DATE_FORMAT;
import static com.vmo.freshermanagement.intern.constant.ServiceConstant.DATE_TIME_FORMAT;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class CenterServiceTests {

    @Mock
    private CenterRepository centerRepository;

    @Mock
    private FresherRepository fresherRepository;

    @InjectMocks
    private CenterServiceImpl centerService;

    private Center center1;
    private Center center2;
    private Fresher fresher;
    private String MANAGER = "MANAGER";
    private String ADMIN = "ADMIN";
    private String CREATED_DATE_TIME = "12:34:56 07/08/2000";

    @BeforeEach
    public void setup() {
        center1 = Center.builder()
                .id(1)
                .name("Gia Lam Center")
                .address("S2 Ocean park Gia Lam")
                .phone("0324512458")
                .description("Description")
                .createdBy(MANAGER)
                .createdDate(dateTimeValue(CREATED_DATE_TIME))
                .build();

        center2 = Center.builder()
                .id(2)
                .name("Duy Tan Center")
                .address("9 Duy Tan, Cau Giay")
                .phone("0395674217")
                .description("Mô tả")
                .createdBy(MANAGER)
                .createdDate(dateTimeValue(CREATED_DATE_TIME))
                .build();

        fresher = Fresher.builder()
                .name("Văn")
                .dob(dateValue("01/01/2000"))
                .gender(genderValue("Nam"))
                .email("congvan@gmail.com")
                .phone("0123456789")
                .position("Back-end")
                .language("Java")
                .joinedDate(dateValue("02/10/2023"))
                .build();
    }

    // JUnit test for getAllCenter method
    @Test
    @DisplayName("JUnit test for GetAllCenter method")
    public void givenCenterList_whenGetAllCenter_thenReturnCenterList() {
        // given - precondition or setup
        given(centerRepository.findAll()).willReturn(List.of(center1, center2));

        // when - action or behaviour that we are going to test
        List<Center> centers = centerService.getAllCenter();

        // then - verify the output
        assertThat(centers).isNotNull();
        assertThat(centers).hasSize(2);
    }

    // JUnit test for updateCenter method
    @Test
    @DisplayName("JUnit test for updateCenter method")
    public void givenCenterId_whenUpdateCenter_thenReturnUpdatedCenter() {
        // given - precondition or setup
        String name = "New name", phone = "0123456789", address = "New Address", description = "New Description";
        given(centerRepository.findById(1)).willReturn(Optional.of(center1));
        given(centerRepository.save(center1)).willReturn(center1);

        // when - action or behaviour that we are going to test
        Center updatedCenter = centerService.updateCenter(center1.getId(), ADMIN, name, phone, address, description);

        // then - verify the output
        assertThat(updatedCenter.getUpdatedBy()).isEqualTo(ADMIN);
        assertThat(updatedCenter.getName()).isEqualTo(name);
        assertThat(updatedCenter.getPhone()).isEqualTo(phone);
        assertThat(updatedCenter.getAddress()).isEqualTo(address);
        assertThat(updatedCenter.getDescription()).isEqualTo(description);
    }

    // JUnit test for updateCenter method which throws exception
    @Test
    @DisplayName("JUnit test for updateCenter method which throws exception")
    public void givenCenterId_whenUpdateCenter_thenThrowsException() {
        int centerId = 1;
        // given - precondition or setup
        String name = "New name", phone = "0123456789", address = "New Address", description = "New Description";
        given(centerRepository.findById(centerId)).willReturn(Optional.empty());

        // when - action or behaviour that we are going to test
        Assertions.assertThrows(CenterNotFoundException.class, () -> {
            Center updatedCenter = centerService.updateCenter(centerId, ADMIN, name, phone, address, description);
        });


        // then - verify the output
        verify(centerRepository, never()).save(center1);
    }

    // JUnit test for deleteCenterById method
    @Test
    @DisplayName("JUnit test for deleteCenterById method")
    public void givenCenterId_whenDeleteCenterById_thenNothing() {
        int centerId = 2;
        // given - precondition or setup
        given(centerRepository.findById(centerId)).willReturn(Optional.of(center2));
        given(fresherRepository.findAllByCenterId(centerId)).willReturn(Collections.emptyList());
        willDoNothing().given(centerRepository).deleteById(centerId);

        // when - action or behaviour that we are going to test
        centerService.deleteCenterById(centerId);

        // then - verify the output
        verify(centerRepository, times(1)).deleteById(centerId);
    }

    // JUnit test for transferFresherToCenter method
    @Test
    @DisplayName("JUnit test for transferFresherToCenter method")
    public void givenFresherIdCenterId_whenTransferFresherToCenter_thenReturnUpdatedFresher() {
        // given - precondition or setup

        // when - action or behaviour that we are going to test
        centerService.transferFresherToCenter(fresher, center2);

        // then - verify the output
        assertThat(fresher.getCenter()).isNotNull();
        assertThat(fresher.getCenter().getId()).isEqualTo(center2.getId());
    }

    private LocalDate dateValue(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(date, dtf);
    }

    private LocalDateTime dateTimeValue(String dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return LocalDateTime.parse(dateTime, dtf);
    }

    private Gender genderValue(String name) {
        return Gender.valueOf(name);
    }
}
