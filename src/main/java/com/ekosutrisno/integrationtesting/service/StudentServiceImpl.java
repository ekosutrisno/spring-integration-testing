package com.ekosutrisno.integrationtesting.service;

import com.ekosutrisno.integrationtesting.entity.Student;
import com.ekosutrisno.integrationtesting.exception.BadRequestException;
import com.ekosutrisno.integrationtesting.exception.StudentNotFoundException;
import com.ekosutrisno.integrationtesting.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Eko Sutrisno
 * Wednesday, 31/03/2021 10:20
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public void addStudent(Student student) {
        Boolean existsEmail = studentRepository.selectExistsEmail(student.getEmail());

        if (existsEmail) {
            throw new BadRequestException("Email " + student.getEmail() + " taken");
        }

        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("Student with id " + studentId + " does not exists");
        }

        studentRepository.deleteById(studentId);
    }
}
