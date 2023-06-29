package com.ust.book.service;

import com.ust.book.domain.Book;
import com.ust.book.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findByIsbn(long isbn);
    List<Book> findAll();

    List<Book> findByAllIsbn(List<String> isbns);
}
