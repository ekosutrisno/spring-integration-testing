package com.ekosutrisno.integrationtesting.service;

import com.ekosutrisno.integrationtesting.entity.Book;
import com.ekosutrisno.integrationtesting.entity.Student;
import com.ekosutrisno.integrationtesting.exception.StudentNotFoundException;
import com.ekosutrisno.integrationtesting.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Eko Sutrisno
 * Tuesday, 06/04/2021 9:52
 */

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookServiceUnderTest;

    @BeforeEach
    void setUp() {
        bookServiceUnderTest = new BookServiceImpl(bookRepository);
    }

    @Test
    void getAllBooks() {
        // When
        bookServiceUnderTest.getAllBooks();

        // Then
        verify(bookRepository).findAll();
    }

    @Test
    void addBook() {
        // Given
        Book book = new Book(
                "Java Basic",
                "Eko Sutrisno",
                LocalDate.now()
        );

        // When
        bookServiceUnderTest.addBook(book);

        // Then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor
                .forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book bookCaptured = bookArgumentCaptor.getValue();

        assertThat(bookCaptured).isEqualTo(book);
    }

    @Test
    void updateBook() {
        // Given
        long bookId = 2L;

        given(bookRepository.findById(bookId))
                .willReturn(
                        Optional.of(
                                new Book(2L,
                                        "Java Basic",
                                        "Eko Sutrisno",
                                        LocalDate.of(2021, 1, 14)
                                )
                        )
                );

        Book book = new Book(
                "Java Basic v1",
                "Eko Sutrisno",
                LocalDate.of(2021, 1, 14)
        );

        // When
        bookServiceUnderTest.updateBook(bookId, book);

        // Then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book bookCaptured = bookArgumentCaptor.getValue();

        assertThat(bookCaptured).isEqualTo(bookCaptured);

    }

    @Test
    void deleteBook() {

        // Given
        long bookId = 12L;

        given(bookRepository.existsById(bookId))
                .willReturn(Boolean.TRUE);

        // When
        bookServiceUnderTest.deleteBook(bookId);

        // Then
        verify(bookRepository).deleteById(bookId);

    }

    @Test
    void willThrowWhenDeleteBookNotFound() {
        // Given
        long bookId = 12L;

        given(bookRepository.existsById(bookId))
                .willReturn(Boolean.FALSE);

        // When -> Then
        assertThatThrownBy(() -> bookServiceUnderTest.deleteBook(bookId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Book with Id" + bookId + "Not Found");

        verify(bookRepository, never()).deleteById(bookId);

    }
}