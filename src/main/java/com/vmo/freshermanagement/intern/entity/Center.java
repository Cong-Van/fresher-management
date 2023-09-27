package com.vmo.freshermanagement.intern.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "centers")
public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    @Pattern(regexp = "^0[0-9]{9}", message = "Phone number should be start with '0' and has 10 numbers")
    private String phone;

    @Column(name = "created_date")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "description")
    private String description;

}
