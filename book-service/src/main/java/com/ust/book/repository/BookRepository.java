package com.ust.book.repository;

import com.ust.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIsbn(long isbn);
    List<Book> findByTitle(String title);
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:word%")
    List<Book> findByTitleContainingWord(String word);

    @Query("SELECT b FROM Book b WHERE b.author LIKE %:word%")
    List<Book> findByAuthorContainingWord(String word);

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c IN :categories")
    List<Book> findByCategories(@Param("categories") String[] categories);

}
