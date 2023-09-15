package com.vmo.freshermanagement.intern.entity;

import com.vmo.freshermanagement.intern.common.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.time.LocalDate;

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
    private String name;

    @Column(name = "dob")
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
    private String position;

    @Column(name = "language")
    private String language;

    @Column(name = "mark1")
    private int mark1;

    @Column(name = "mark2")
    private int mark2;

    @Column(name = "mark3")
    private int mark3;

    @Column(name = "markAvg")
    private double markAvg;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "center_id")
    private Center center;

    public void setMarkAvg() {
        DecimalFormat df = new DecimalFormat("#.#");
        this.markAvg = Double.parseDouble(df.format((double) (mark1 + mark2 + mark3) / 3));
    }
}
