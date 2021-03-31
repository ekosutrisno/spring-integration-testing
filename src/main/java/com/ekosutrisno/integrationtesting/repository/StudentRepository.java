package com.ekosutrisno.integrationtesting.repository;

import com.ekosutrisno.integrationtesting.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Eko Sutrisno
 * Wednesday, 31/03/2021 9:55
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Student s WHERE s.email = ?1")
    Boolean selectExistsEmail(String email);
}
