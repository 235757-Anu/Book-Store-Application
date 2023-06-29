package com.ust.book.controller;

import com.ust.book.domain.Book;
import com.ust.book.dto.BookDto;
import com.ust.book.dto.CreateDto;
import com.ust.book.dto.UpdateDto;
import com.ust.book.exception.BookAlreadyExistsException;
import com.ust.book.exception.BookNotFoundException;
import com.ust.book.exception.NoBooksFoundException;
import com.ust.book.service.AdminBookService;
import com.ust.book.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/admin/books")
public class AdminBookController {

    @Autowired
    private AdminBookService adminBookService;

    @Autowired
    private BookService bookService;

    private Logger logger = LoggerFactory.getLogger(AdminBookController.class);

    @PostMapping("/add")
    public ResponseEntity<BookDto> addBook(@Valid @RequestBody CreateDto createDto){
        logger.info("createBook: Creating book with title {}", createDto.title());
        bookService.findByIsbn(createDto.isbn())
                .ifPresent(movie -> {
                    logger.error("createBook: Book with isbn {} already exists", createDto.isbn());
                    throw new BookAlreadyExistsException(
                            String.format("Book with isbn %s already exists", createDto.isbn())
                    );
                });
        Book book = adminBookService.save(CreateDtoToEntity(createDto));
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getIsbn()).toUri()
        ).body(EntityToDto(book));
//        final var book=CreateDtoToEntity(createDto);
//        final var result=bookService.findByIsbn(book.getIsbn());
//        if(result.isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).body(EntityToDto(adminBookService.save(book)));
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBook(){
        logger.info("getAllBooks: Fetching all books");
        final var books = bookService.findAll();
        if(books.isEmpty()){
            logger.error("getAllBooks: No Books found");
            throw new NoBooksFoundException(
                    String.format("Books with id %d not found")
            );
        }
        final var bookList = books.stream().map(this::EntityToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(bookList);
//        final var books=bookService.findAll();
//        if(books.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        }
//        final var bookList=books.stream().map(this::EntityToDto).toList();
//        return ResponseEntity.status(HttpStatus.OK).body(bookList);
    }

    @GetMapping("/view/{isbn}")
    public ResponseEntity<BookDto> getByIsbn(@PathVariable("isbn") long isbn){
        logger.info("getBook: Fetching book with isbn {}", isbn);
        var book = bookService.findByIsbn(isbn);
        if (book.isEmpty()) {
            logger.error("getBook: Book with isbn {} not found", isbn);
            throw new BookNotFoundException(
                    String.format("Book with isbn %s not found", isbn)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(EntityToDto(book.get()));
//        final var book=bookService.findByIsbn(isbn);
//        if(book.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(EntityToDto(book.get()));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("isbn") long isbn, @RequestBody UpdateDto updateDto) {
        logger.info("updateBook: Updating book with isbn {}", isbn);
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
        logger.error("updateMovie: Movie with isbn {} not found", isbn);
        throw new BookNotFoundException(
                String.format("Book with isbn %s not found", isbn)
        );
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/{isbn}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable("isbn") long isbn){
        logger.info("deleteBook: Deleting book with isbn {}",isbn);
        final var book = bookService.findByIsbn(isbn);
        if(book.isEmpty()){
            logger.error("deleteBook: Book with id {} not found",isbn);
            throw new BookNotFoundException(
                    String.format("Book with isbn %s not found", isbn)
            );
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        adminBookService.delete(book.get().getBookId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/rating")
    public ResponseEntity<BookDto> updateRating(@RequestParam long isbn,@RequestParam double rating){
        logger.info("updateBook: Update book rating with isbn {}", isbn);
        var book = bookService.findByIsbn(isbn);
        if(book.isPresent()) {
            if (rating!=0.0) {
                book.get().setRating(rating);
            }
            adminBookService.update(book.get());
            return ResponseEntity.ok(EntityToDto(book.get()));
        }
        logger.error("updateMovie: Movie with isbn {} not found", isbn);
        throw new BookNotFoundException(
                String.format("Book with isbn %s not found", isbn)
        );
    }



//    public Book DtoToEntity(BookDto bookDto){
//        return new Book(bookDto.bookId(), bookDto.isbn(), bookDto.title(), bookDto.author(), bookDto.summary(), bookDto.language(),
//                bookDto.pageCount(), bookDto.publishYear(), bookDto.imageUrl());
//    }

    public BookDto EntityToDto(Book book){
        return new BookDto(book.getBookId(), book.getIsbn(), book.getTitle(), book.getAuthor(), book.getCategories(), book.getSummary(), book.getLanguage(),
                book.getPageCount(), book.getPublishYear(), book.getImageUrl(), book.getRating());
    }

    public Book CreateDtoToEntity(CreateDto createDto){
        return new Book(0,createDto.isbn(), createDto.title(), createDto.author(), createDto.categories(), createDto.summary(), createDto.language(),
                createDto.pageCount(), createDto.publishYear(),createDto.imageUrl(),createDto.rating());
    }

}
