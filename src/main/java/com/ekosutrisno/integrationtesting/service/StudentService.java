package com.ekosutrisno.integrationtesting.service;

import com.ekosutrisno.integrationtesting.entity.Student;

import java.util.List;

/**
 * @author Eko Sutrisno
 * Wednesday, 31/03/2021 10:12
 */
public interface StudentService {
    List<Student> getAllStudent();

    void addStudent(Student student);

    void deleteStudent(Long studentId);
}
