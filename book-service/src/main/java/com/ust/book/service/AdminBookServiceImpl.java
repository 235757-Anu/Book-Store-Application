package com.ust.book.service;

import com.ust.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminBookServiceImpl implements AdminBookService {
    @Autowired
    private BookRepository bookRepository;
}
