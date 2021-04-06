package com.ekosutrisno.integrationtesting.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Eko Sutrisno
 * Tuesday, 06/04/2021 9:24
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = Book.TABLE_NAME)
public class Book {

    public static final String TABLE_NAME = "tbl_book";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false, updatable = false)
    private Long bookId;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "book_author", nullable = false)
    private String bookAuthor;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    public Book(String bookName, String bookAuthor, LocalDate publishedDate) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.publishedDate = publishedDate;
    }

    public Book(Long bookId, String bookName, String bookAuthor, LocalDate publishedDate) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.publishedDate = publishedDate;
    }
}
