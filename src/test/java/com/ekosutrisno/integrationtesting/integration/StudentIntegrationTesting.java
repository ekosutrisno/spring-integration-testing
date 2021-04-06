package com.ekosutrisno.integrationtesting.integration;

import com.ekosutrisno.integrationtesting.entity.Gender;
import com.ekosutrisno.integrationtesting.entity.Student;
import com.ekosutrisno.integrationtesting.repository.StudentRepository;
import com.ekosutrisno.integrationtesting.service.StudentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-qa.properties")
@AutoConfigureMockMvc
class StudentIntegrationTesting {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    protected StudentRepository studentRepository;

    private final Faker faker = new Faker();

    @Test
    void canRegisterNewStudent() throws Exception {
        // Given
        String name = String.format("%s %s", faker.name().firstName(),
                faker.name().lastName());

        String email = String.format("%s@exoapp.com", StringUtils.trimAllWhitespace(name.trim().toLowerCase()));

        Student student = new Student(
                name,
                email,
                Gender.MALE
        );

        // When
        ResultActions resultActions = mockMvc
                .perform(
                        post("/api/v1/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isOk());

        // Then
        resultActions.andExpect(status().isOk());
        List<Student> students = studentService.getAllStudent();
        assertThat(students)
                .usingElementComparatorIgnoringFields("id")
                .contains(student);
    }

    @Test
    void canDeleteStudent() throws Exception {
        // Given
        String name = String.format("%s %s", faker.name().firstName(),
                faker.name().lastName());

        String email = String.format("%s@exoapp.com", StringUtils.trimAllWhitespace(name.trim().toLowerCase()));

        Student student = new Student(name, email, Gender.MALE);

        mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult getStudentResult = mockMvc.perform(get("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = getStudentResult
                .getResponse().getContentAsString();

        List<Student> students = objectMapper
                .readValue(contentAsString, new TypeReference<>() {
                });

        long studentId = students.stream()
                .filter(st -> st.getEmail().equals(student.getEmail()))
                .map(Student::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalThreadStateException("student with email: " + email + " not found"));

        // When
        ResultActions resultActions = mockMvc
                .perform(delete("/api/v1/students/" + studentId));

        // Then
        resultActions.andExpect(status().isOk());
        boolean exist = studentRepository.existsById(studentId);
        assertThat(exist).isFalse();

    }
}
