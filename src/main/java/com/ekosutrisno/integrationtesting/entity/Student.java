package com.ekosutrisno.integrationtesting.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Eko Sutrisno
 * Wednesday, 31/03/2021 9:46
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = Student.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = "u_email", columnNames = {"email"})
        })
public class Student {

    static final String TABLE_NAME = "tbl_student";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;


    @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;


    public Student(@NotBlank String name, @Email String email, @NotNull Gender gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
    }
}
