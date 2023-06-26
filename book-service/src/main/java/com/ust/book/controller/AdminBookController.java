package com.ust.book.controller;

import com.ust.book.domain.Book;
import com.ust.book.dto.BookDto;
import com.ust.book.dto.CreateDto;
import com.ust.book.dto.UpdateDto;
import com.ust.book.service.AdminBookService;
import com.ust.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/admin/books")
public class AdminBookController {

    @Autowired
    private AdminBookService adminBookService;

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<BookDto> addBook(@RequestBody CreateDto createDto){
        final var book=CreateDtoToEntity(createDto);
        final var result=bookService.findByIsbn(book.getIsbn());
        if(result.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityToDto(adminBookService.save(book)));
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBook(){
        final var books=bookService.findAll();
        if(books.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        final var bookList=books.stream().map(this::EntityToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/view/{isbn}")
    public ResponseEntity<BookDto> getByIsbn(@PathVariable long isbn){
        final var book=bookService.findByIsbn(isbn);
        if(book.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(EntityToDto(book.get()));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("isbn") long isbn, @RequestBody UpdateDto updateDto) {
        var book = bookService.findByIsbn(isbn);
        if(book.isPresent()){
            if(!updateDto.title().isEmpty()){
                book.get().setTitle(updateDto.title());
            }
            if (!updateDto.author().isEmpty()){
                book.get().setAuthor(updateDto.author());
            }
            if(!updateDto.summary().isEmpty()){
                book.get().setSummary(updateDto.summary());
            }
            if(updateDto.publishYear()!=0){
                book.get().setPublishYear(updateDto.publishYear());
            }
            if(updateDto.pageCount()!=0){
                book.get().setPageCount(updateDto.pageCount());
            }
            adminBookService.update(book.get());
            return ResponseEntity.ok(EntityToDto(book.get()));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
    @DeleteMapping("/{isbn}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable long isbn){
        final var book = bookService.findByIsbn(isbn);
        if(book.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        adminBookService.delete(book.get().getBookId());
        return ResponseEntity.noContent().build();
    }



    public Book DtoToEntity(BookDto bookDto){
        return new Book(bookDto.bookId(), bookDto.isbn(), bookDto.title(), bookDto.author(), bookDto.summary(), bookDto.language(),
                bookDto.pageCount(), bookDto.publishYear(), bookDto.imageUrl());
    }

    public BookDto EntityToDto(Book book){
        return new BookDto(book.getBookId(), book.getIsbn(), book.getTitle(), book.getAuthor(), book.getSummary(), book.getLanguage(),
                book.getPageCount(), book.getPublishYear(), book.getImageUrl());
    }

    public Book CreateDtoToEntity(CreateDto createDto){
        return new Book(0,createDto.isbn(), createDto.title(), createDto.author(), createDto.summary(), createDto.language(),
                createDto.pageCount(), createDto.publishYear(),createDto.imageUrl());
    }

}
