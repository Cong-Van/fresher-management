package com.vmo.freshermanagement.intern.service;

import com.vmo.freshermanagement.intern.common.Gender;
import com.vmo.freshermanagement.intern.entity.Fresher;
import com.vmo.freshermanagement.intern.exception.EmailExistException;
import com.vmo.freshermanagement.intern.exception.PhoneExistException;
import com.vmo.freshermanagement.intern.repository.FresherRepository;
import com.vmo.freshermanagement.intern.service.impl.FresherServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.vmo.freshermanagement.intern.constant.ServiceConstant.DATE_FORMAT;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class FresherServiceTests {

    @Mock
    private FresherRepository fresherRepository;

    @InjectMocks
    private FresherServiceImpl fresherService;

    private Fresher fresher1;
    private Fresher fresher2;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
    DecimalFormat df = new DecimalFormat("#.#");

    @BeforeEach
    public void setup() {

        fresher1 = Fresher.builder()
                .name("Văn")
                .dob(dateValue("01/01/2000"))
                .gender(genderValue("Nam"))
                .email("congvan@gmail.com")
                .phone("0123456789")
                .position("Back-end")
                .language("Java")
                .joinedDate(dateValue("02/10/2023"))
                .build();

        fresher2 = Fresher.builder()
                .name("Hà")
                .dob(dateValue("02/02/2002"))
                .gender(genderValue("Nữ"))
                .email("hathanh@gmail.com")
                .phone("0987654321")
                .position("BA")
                .language("Python")
                .joinedDate(dateValue("02/10/2023"))
                .build();
    }

    // JUnit test for updateFresher method
    @Test
    @DisplayName("JUnit test for updateFresher method")
    public void givenFresherId_whenUpdateFresher_thenReturnUpdatedFresher() {
        // given - precondition or setup
        int fresherId = 1;
        String newEmail = "newEmail@gmail.com", newPhone = "0113114115";
        String newName = "newName", newDob = "31/12/2001", newGender = "Other",
                newPosition = "Front-end", newLanguage = "NodeJS";
        given(fresherRepository.findById(fresherId)).willReturn(Optional.of(fresher1));
        given(fresherRepository.findByEmail(newEmail)).willReturn(null);
        given(fresherRepository.findByPhone(newPhone)).willReturn(null);
        given(fresherRepository.save(fresher1)).willReturn(fresher1);

        // when - action or behaviour that we are going to test
        Fresher updatedFresher = fresherService.updateFresher(fresherId, newName, newDob, newGender,
                newPhone, newEmail, newPosition, newLanguage);

        // then - verify the output
        assertThat(updatedFresher.getName()).isEqualTo(newName);
        assertThat(updatedFresher.getDob().format(dtf)).hasToString(newDob);
        assertThat(updatedFresher.getGender().toString()).hasToString(newGender);
        assertThat(updatedFresher.getPhone()).isEqualTo(newPhone);
        assertThat(updatedFresher.getEmail()).isEqualTo(newEmail);
        assertThat(updatedFresher.getPosition()).isEqualTo(newPosition);
        assertThat(updatedFresher.getLanguage()).isEqualTo(newLanguage);
    }

    // JUnit test for updateFresher method throws EmailExistException
    @Test
    @DisplayName("JUnit test for updateFresher method throws EmailExistException")
    public void givenFresherId_whenUpdateFresher_thenThrowsEmailExistException() {
        // given - precondition or setup
        int fresherId = 1;
        String newEmail = "hathanh@gmail.com", newPhone = "0113114115";
        String newName = "newName", newDob = "31/12/2001", newGender = "Other",
                newPosition = "Front-end", newLanguage = "NodeJS";
        given(fresherRepository.findById(fresherId)).willReturn(Optional.of(fresher1));
        given(fresherRepository.findByEmail(newEmail)).willReturn(fresher2);

        // when - action or behaviour that we are going to test
        assertThrows(EmailExistException.class, () -> {
            Fresher updatedFresher = fresherService.updateFresher(fresherId, newName, newDob, newGender,
                    newPhone, newEmail, newPosition, newLanguage);
        });

        // then - verify the output
        verify(fresherRepository, never()).save(fresher1);
    }

    // JUnit test for updateFresher method throws PhoneExistException
    @Test
    @DisplayName("JUnit test for updateFresher method throws PhoneExistException")
    public void givenFresherId_whenUpdateFresher_thenThrowsPhoneExistException() {
        // given - precondition or setup
        int fresherId = 1;
        String newEmail = "newEmail@gmail.com", newPhone = "0987654321";
        String newName = "newName", newDob = "31/12/2001", newGender = "Other",
                newPosition = "Front-end", newLanguage = "NodeJS";
        given(fresherRepository.findById(fresherId)).willReturn(Optional.of(fresher1));
        given(fresherRepository.findByEmail(newEmail)).willReturn(null);
        given(fresherRepository.findByPhone(newPhone)).willReturn(fresher2);

        // when - action or behaviour that we are going to test
        assertThrows(PhoneExistException.class, () -> {
            Fresher updatedFresher = fresherService.updateFresher(fresherId, newName, newDob, newGender,
                    newPhone, newEmail, newPosition, newLanguage);
        });

        // then - verify the output
        verify(fresherRepository, never()).save(fresher1);
    }

    // JUnit test for updateMark method
    @Test
    @DisplayName("JUnit test for updateMark method")
    public void givenFresherObject_whenUpdateMark_thenUpdatedFresher() {
        // given - precondition or setup
        double mark1 = 5.0, mark2 = 8.0, mark3 = 10.0;
        double markAvg = Double.parseDouble(df.format((mark1 + mark2 + mark3) / 3));
        given(fresherRepository.save(fresher1)).willReturn(fresher1);

        // when - action or behaviour that we are going to test
        fresherService.updateMark(fresher1, mark1, mark2, mark3);

        // then - verify the output
        assertThat(fresher1.getMark1()).isEqualTo(mark1);
        assertThat(fresher1.getMark2()).isEqualTo(mark2);
        assertThat(fresher1.getMark3()).isEqualTo(mark3);
        assertThat(fresher1.getMarkAvg()).isEqualTo(markAvg);
    }

    private LocalDate dateValue(String date) {
        return LocalDate.parse(date, dtf);
    }

    private Gender genderValue(String name) {
        return Gender.valueOf(name);
    }

}
