package com.ust.book.controller;

import com.ust.book.domain.Book;
import com.ust.book.dto.BookDto;
import com.ust.book.dto.CreateDto;
import com.ust.book.dto.ToListDto;
import com.ust.book.exception.BookNotFoundException;
import com.ust.book.exception.NoBooksFoundException;
import com.ust.book.repository.BookRepository;
import com.ust.book.service.BookService;
import com.ust.book.service.UserBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(UserBookController.class);

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getByIsbn(@PathVariable("isbn") long isbn) {
        logger.info("getBook: Fetching book with isbn {}", isbn);
        var book = bookService.findByIsbn(isbn);
        if (book.isEmpty()) {
            logger.error("getBook: Fetching book with isbn {} not found", isbn);
            throw new BookNotFoundException(
                    String.format("Book with isbn %s not found", isbn)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(EntityToDto(book.get()));
    }
    @PostMapping("/isbn")
    public ResponseEntity<ToListDto> getAllByIsbn(@RequestBody List<String> isbns){
        logger.info("getAllByIsbn: Fetching all book with isbns {}", isbns.toString());
        List<Book> books = bookService.findByAllIsbn(isbns);
        if(books.isEmpty()){
            logger.error("getAllByIsbn: Fetching book with isbns {} not found", isbns.toString());
            throw new NoBooksFoundException(
                    String.format("Book with isbn %s not found", isbns.toString())
            );
        }
        List<BookDto> bookDtoList = books.stream().map(this::EntityToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ToListDto(bookDtoList));
    }
    @PostMapping("/filter")
    public ResponseEntity<ToListDto> getAllBookFilter(
            @RequestBody String[] categories )
    {
        logger.info("getAllBookFilter: Fetching all book with categories {}", categories);
        List<Book> books;
        if (categories != null && categories.length!=0) {
            // Filter by categories only
            books = userBookService.findByCategories(categories);
        } else {
            // No filters applied, retrieve all books
            books = bookService.findAll();
        }
        if (books.isEmpty()) {
            logger.error("getAllBookFilter: No books found for the filter {} ", categories);
            throw new NoBooksFoundException(
                    String.format("No Books found with isbns %s", categories)
            );
        }
        List<BookDto> bookDtoList = books.stream().map(this::EntityToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ToListDto(bookDtoList));
    }
    @GetMapping("/title")
    public ResponseEntity<ToListDto> getBookByTitle(@RequestParam(value = "title", required = false) String title)
    {
        logger.info("getAllBookFilter: Fetching all book with title {}", title);
        List<Book> books;
        if (!title.isEmpty()  ) {
            books = userBookService.findByTitle(title);
        } else {
            books = bookService.findAll();
        }
        if (books.isEmpty()) {
            logger.error("getAllByTitle: Fetching book with title {} not found", title);
            throw new NoBooksFoundException(
                    String.format("Book with title %s not found", title)
            );
        }
        List<BookDto> bookDtoList = books.stream().map(this::EntityToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ToListDto(bookDtoList));
    }
    @GetMapping("/author")
    public ResponseEntity<ToListDto> getAllBookByAuthor(@RequestParam(value = "author", required = false) String author)
    {
        logger.info("getAllBookFilter: Fetching all book with author {}", author);
        List<Book> books;
        if (!author.isEmpty()  ) {
            // Filter by categories only
            books = userBookService.findByAuthor(author);
        } else {
            // No filters applied, retrieve all books
            books = bookService.findAll();
        }
        if (books.isEmpty()) {
            logger.error("getAllByIsbn: Fetching book with author {} not found", author);
            throw new NoBooksFoundException(
                    String.format("Book with author %s not found", author)
            );
        }
        List<BookDto> bookDtoList = books.stream().map(this::EntityToDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ToListDto(bookDtoList));
    }
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
        return new BookDto(book.getBookId(), book.getIsbn(), book.getTitle(), book.getAuthor(), book.getCategories(), book.getSummary(), book.getLanguage(),
                book.getPageCount(), book.getPublishYear(), book.getImageUrl(), book.getRating());
    }

    public Book CreateDtoToEntity(CreateDto createDto){
        return new Book(0,createDto.isbn(), createDto.title(), createDto.author(), createDto.categories(), createDto.summary(), createDto.language(),
                createDto.pageCount(), createDto.publishYear(),createDto.imageUrl(),createDto.rating());
    }

}
