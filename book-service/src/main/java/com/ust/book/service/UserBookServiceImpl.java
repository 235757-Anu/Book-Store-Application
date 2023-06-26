package com.ust.book.service;

import com.ust.book.domain.Book;
import com.ust.book.dto.BookDto;
import com.ust.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBookServiceImpl implements UserBookService{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleContainingWord(title);
    }

}
