package com.ekosutrisno.integrationtesting.service;

import com.ekosutrisno.integrationtesting.entity.Book;
import com.ekosutrisno.integrationtesting.exception.StudentNotFoundException;
import com.ekosutrisno.integrationtesting.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Eko Sutrisno
 * Tuesday, 06/04/2021 9:36
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long bookId, Book book) {
        Optional<Book> optionalBook = bookRepository
                .findById(bookId);

        if (optionalBook.isPresent()) {
            Book bookFromDb = optionalBook.get();

            bookFromDb.setBookName(book.getBookName());
            bookFromDb.setBookAuthor(book.getBookAuthor());
            bookFromDb.setPublishedDate(book.getPublishedDate());

            return bookRepository.save(bookFromDb);
        }

        return null;
    }

    @Override
    public Boolean deleteBook(Long bookId) {

        if (!bookRepository.existsById(bookId)) {
            throw new StudentNotFoundException("Book with Id" + bookId + "Not Found");
        }

        bookRepository.deleteById(bookId);
        return Boolean.TRUE;
    }
}
