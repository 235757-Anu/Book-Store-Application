package com.ust.book.controller;

import com.ust.book.domain.Book;
import com.ust.book.dto.BookDto;
import com.ust.book.repository.BookRepository;
import com.ust.book.service.BookService;
import com.ust.book.service.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user/books")
public class UserBookController {
    @Autowired
    private UserBookService userBookService;
    @Autowired
    private BookService bookService;

    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDto>> getAllBookByTitle(@PathVariable String title) {
        final var books = userBookService.findByTitle(title);

        if (!books.isEmpty()) {
            List<BookDto> bookDtos = books.stream().map(this::EntityToDto).toList();
            return ResponseEntity.ok().body(bookDtos);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    public BookDto EntityToDto(Book book){
        return new BookDto(book.getBookId(), book.getIsbn(), book.getTitle(), book.getAuthor(), book.getSummary(), book.getLanguage(),
                book.getPageCount(), book.getPublishYear(), book.getImageUrl());
    }

}
