package com.ust.book.service;

import com.ust.book.domain.Book;

import java.util.Optional;

public interface AdminBookService {

    Book save(Book book);
    void update(Book book);
    void delete(long bookId);
}
