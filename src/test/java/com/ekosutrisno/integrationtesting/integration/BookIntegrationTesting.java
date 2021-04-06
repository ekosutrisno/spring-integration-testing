package com.ekosutrisno.integrationtesting.integration;

import com.ekosutrisno.integrationtesting.entity.Book;
import com.ekosutrisno.integrationtesting.repository.BookRepository;
import com.ekosutrisno.integrationtesting.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Eko Sutrisno
 * Tuesday, 06/04/2021 10:36
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-qa.properties")
@AutoConfigureMockMvc
class BookIntegrationTesting {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private final Faker faker = new Faker();


    @Test
    @Disabled
    void getAllBook() {
    }

    @Test
    void CanAddBook() throws Exception {
        // Given
        Book book = new Book(faker.book().title(), faker.book().author(), LocalDate.now());

        // When
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)));

        // Then
        resultActions.andExpect(status().isOk());
        List<Book> books = bookService.getAllBooks();
        assertThat(books)
                .usingElementComparatorIgnoringFields("bookId")
                .contains(book);

    }

    @Test
    void canUpdateBook() throws Exception {
        // Given
        Book book = new Book(faker.book().title(), faker.book().author(), LocalDate.now());

        mockMvc
                .perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)));

        MvcResult getBookResult = mockMvc.perform(get("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = getBookResult
                .getResponse().getContentAsString();

        List<Book> books = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        long bookId = books.stream()
                .filter(b -> b.getBookName().equals(book.getBookName()))
                .map(Book::getBookId)
                .findFirst()
                .orElseThrow(() -> new IllegalThreadStateException("Book Not Found"));

        // Book to update
        Book bookToUpdate = new Book(faker.book().title(), faker.book().author(), LocalDate.now());

        // When
        ResultActions resultActions = mockMvc.perform(put("/api/v1/books/" + bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookToUpdate)));

        // Then
        resultActions.andExpect(status().isOk());

    }

    @Test
    void canDeleteBookById() throws Exception {
        // Given
        Book book = new Book(faker.book().title(), faker.book().author(), LocalDate.now());

        mockMvc
                .perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)));

        MvcResult getBookResult = mockMvc.perform(get("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = getBookResult
                .getResponse().getContentAsString();

        List<Book> books = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        long bookId = books.stream()
                .filter(b -> b.getBookName().equals(book.getBookName()))
                .map(Book::getBookId)
                .findFirst()
                .orElseThrow(() -> new IllegalThreadStateException("Book Not Found"));

        // When
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/books/" + bookId));

        // Then
        resultActions.andExpect(status().isOk());
        boolean existById = bookRepository.existsById(bookId);
        assertThat(existById).isFalse();
    }
}