package com.ust.book.service;

import com.ust.book.domain.Book;
import com.ust.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminBookServiceImpl implements AdminBookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void update(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void delete(long bookId) {
        bookRepository.deleteById(bookId);
    }
}
