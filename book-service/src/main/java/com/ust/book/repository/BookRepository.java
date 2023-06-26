package com.ust.book.repository;

import com.ust.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIsbn(long isbn);
    List<Book> findByTitle(String title);
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:word%")
    List<Book> findByTitleContainingWord(String word);

}
