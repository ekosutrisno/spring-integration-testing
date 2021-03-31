package com.ekosutrisno.integrationtesting.service;

import com.ekosutrisno.integrationtesting.entity.Gender;
import com.ekosutrisno.integrationtesting.entity.Student;
import com.ekosutrisno.integrationtesting.exception.BadRequestException;
import com.ekosutrisno.integrationtesting.exception.StudentNotFoundException;
import com.ekosutrisno.integrationtesting.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

/**
 * @author Eko Sutrisno
 * Wednesday, 31/03/2021 11:29
 */

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentService studentServiceUnderTest;

    @BeforeEach
    void setUp() {
        studentServiceUnderTest = new StudentServiceImpl(studentRepository);
    }

    @Test
    void getAllStudent() {
        // When
        studentServiceUnderTest.getAllStudent();

        // Then
        verify(studentRepository).findAll();
    }

    @Test
    void addStudent() {
        // Given
        Student student = new Student(
                "Eko Sutrisno",
                "ekosutrisno801@gmail.com",
                Gender.MALE
        );

        // When
        studentServiceUnderTest.addStudent(student);

        // Then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor
                .forClass(Student.class);

        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        Student studentCaptured = studentArgumentCaptor.getValue();

        assertThat(studentCaptured).isEqualTo(student);

    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // Given
        Student student = new Student(
                "Eko Sutrisno",
                "ekosutrisno801@gmail.com",
                Gender.MALE
        );

        given(studentRepository.selectExistsEmail(any()))
                .willReturn(Boolean.TRUE);

        // When -> Then
        assertThatThrownBy(() -> studentServiceUnderTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

        verify(studentRepository, never()).save(any());

    }

    @Test
    void deleteStudent() {

        // Given
        long studentId = 12L;

        given(studentRepository.existsById(studentId))
                .willReturn(Boolean.TRUE);

        // When
        studentServiceUnderTest.deleteStudent(studentId);

        // Then
        verify(studentRepository).deleteById(studentId);
    }

    @Test
    void willThrowWhenDeleteStudentNotFound() {
        // Given
        long studentId = 12L;

        given(studentRepository.existsById(studentId))
                .willReturn(Boolean.FALSE);

        // When -> Then
        assertThatThrownBy(() -> studentServiceUnderTest.deleteStudent(studentId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + studentId + " does not exists");

        verify(studentRepository, never()).deleteById(studentId);
    }
}