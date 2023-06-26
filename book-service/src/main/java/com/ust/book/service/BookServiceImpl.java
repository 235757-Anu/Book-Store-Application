package com.ust.book.service;

import com.ust.book.domain.Book;
import com.ust.book.dto.BookDto;
import com.ust.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Optional<Book> findByIsbn(long isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
