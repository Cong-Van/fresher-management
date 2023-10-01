package com.vmo.freshermanagement.intern.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vmo.freshermanagement.intern.common.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.time.LocalDate;

import static com.vmo.freshermanagement.intern.constant.ServiceConstant.DATE_FORMAT;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "freshers")
public class Fresher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "You need to enter fresher's name")
    private String name;

    @Column(name = "dob")
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDate dob;

    // @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "phone")
    @Pattern(regexp = "^0[0-9]{9}", message = "Phone number should be start with '0' and has 10 numbers")
    private String phone;

    @Column(name = "email")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Incorrect email format")
    private String email;

    @Column(name = "position")
    @NotBlank(message = "You need to enter fresher's position")
    private String position;

    @Column(name = "language")
    @NotBlank(message = "You need to enter fresher's language")
    private String language;

    @Column(name = "joined_date")
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDate joinedDate;

    @Column(name = "graduated_date")
    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDate graduatedDate;

    @Column(name = "mark1")
    @Min(value = 0, message = "Mark needs to be greater than or equal to 0")
    @Max(value = 10, message = "Mark needs to be less than or equal to 10")
    private double mark1;

    @Column(name = "mark2")
    @Min(value = 0)
    @Max(value = 10)
    private double mark2;

    @Column(name = "mark3")
    @Min(value = 0)
    @Max(value = 10)
    private double mark3;

    @Column(name = "markAvg")
    private double markAvg;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "center_id")
    private Center center;

    public void setMarkAvg() {
        DecimalFormat df = new DecimalFormat("#.#");
        this.markAvg = Double.parseDouble(df.format((mark1 + mark2 + mark3) / 3));
    }

}
