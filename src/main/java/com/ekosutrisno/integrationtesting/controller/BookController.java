package com.ekosutrisno.integrationtesting.controller;

import com.ekosutrisno.integrationtesting.entity.Book;
import com.ekosutrisno.integrationtesting.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Eko Sutrisno
 * Tuesday, 06/04/2021 9:44
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<Book> getAllBook() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping(path = "/{bookId}")
    public Book updateBook(@PathVariable("bookId") Long bookId, @RequestBody Book book) {
        return bookService.updateBook(bookId, book);
    }

    @DeleteMapping(path = "/{bookId}")
    public Boolean deleteBookById(@PathVariable("bookId") Long bookId) {
        return bookService.deleteBook(bookId);
    }

}
