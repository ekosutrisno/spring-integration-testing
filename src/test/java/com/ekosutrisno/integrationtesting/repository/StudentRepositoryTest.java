package com.ekosutrisno.integrationtesting.repository;

import com.ekosutrisno.integrationtesting.entity.Gender;
import com.ekosutrisno.integrationtesting.entity.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Eko Sutrisno
 * Wednesday, 31/03/2021 11:10
 */

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepositoryUnderTest;

    @AfterEach
    void tearDown() {
        studentRepositoryUnderTest.deleteAll();
    }

    @Test
    void itShouldCheckWhenStudentEmailExists() {
        // Given
        String email = "ekosutrisno@gmail.com";
        Student student = new Student(
                "Eko Sutrisno",
                email,
                Gender.MALE
        );

        studentRepositoryUnderTest.save(student);

        // When
        boolean expected = studentRepositoryUnderTest.selectExistsEmail(email);

        // Then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckWhenStudentEmailDoesNotExists() {
        // Given
        String email = "ekosutrisno@gmail.com";

        // When
        boolean expected = studentRepositoryUnderTest.selectExistsEmail(email);

        // Then
        assertThat(expected).isFalse();
    }
}