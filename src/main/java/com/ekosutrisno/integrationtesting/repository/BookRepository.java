package com.ekosutrisno.integrationtesting.repository;

import com.ekosutrisno.integrationtesting.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Eko Sutrisno
 * Tuesday, 06/04/2021 9:29
 */
public interface BookRepository extends JpaRepository<Book, Long> {

}
