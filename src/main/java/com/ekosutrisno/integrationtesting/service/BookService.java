package com.ekosutrisno.integrationtesting.service;

import com.ekosutrisno.integrationtesting.entity.Book;

import java.util.List;

/**
 * @author Eko Sutrisno
 * Tuesday, 06/04/2021 9:34
 */
public interface BookService {

    List<Book> getAllBooks();

    Book addBook(Book book);

    Book updateBook(Long bookId, Book book);

    Boolean deleteBook(Long bookId);
}
