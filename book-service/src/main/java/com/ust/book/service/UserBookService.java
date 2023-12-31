package com.ust.book.service;

import com.ust.book.domain.Book;
import com.ust.book.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface UserBookService {
    List<Book> findByCategories(String[] categories);
    List<Book> findByAuthor(String author);
    List<Book> findByTitle(String title);
}
