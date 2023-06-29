package com.ust.book.controller;

import com.ust.book.domain.ReadList;

import com.ust.book.dto.BookDto;
import com.ust.book.dto.BookUserDto;
import com.ust.book.dto.ReadListDto;
import com.ust.book.dto.ToListDto;
import com.ust.book.exception.BookAlreadyAddedException;
import com.ust.book.exception.BookNotFoundException;
import com.ust.book.service.ApiClientBook;
import com.ust.book.service.ApiClientRating;
import com.ust.book.service.ReadListService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@RestController
@RequestMapping("/api/v1/readlist")
@CrossOrigin("*")
@Slf4j
public class ReadListController {
    private Logger logger = LoggerFactory.getLogger(ReadListController.class);

    @Autowired
    ReadListService readListService;
    @Autowired
    ApiClientBook apiClientBook;
    @Autowired
    ApiClientRating apiClientRating;

    @PostMapping("/addtoreadlist")
    public ResponseEntity<ReadListDto> addToReadList(@RequestBody ReadListDto read){
        logger.info("addToFav: user {} adding the book {} to readlist", read.username(),read.isbn());
        final var readList = PostToEntity(read);
        final var result = readListService.findByIsbnAndUsername(Long.valueOf(readList.getIsbn()),readList.getUsername());
        if(result.isPresent()) {
            logger.info("addToReadList: user {} already added the book {} to readlist", read.username(),read.isbn());
            throw new BookAlreadyAddedException(
                    String.format("Book with isbn %s is already added to readlist", read.isbn()));
        }
        readListService.addreadlist(readList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/readlist")
    public ResponseEntity<ToListDto>  getReadList(@RequestParam String username){
        logger.info("ReadList: fetching favourite books using username{}", username);
        final var result = readListService.findByUsername(username);
        if(result.isPresent()){
            List<Long> isbns = result.stream()
                    .map(ReadList::getIsbn)
                    .toList();
            log.info(isbns.toString());
            log.warn(apiClientBook.getAllByIsbn(isbns).getBody().bookDtoList().toString());
            return ResponseEntity.status(HttpStatus.OK).body(new ToListDto(apiClientBook.getAllByIsbn(isbns).getBody().bookDtoList()));
        }
        logger.info("ReadList: fetching books using username{} not found", username);
        throw new BookNotFoundException(
                String.format("User with username %s is has no Readlist", username));
    }

    @GetMapping("/viewreadlist")
    public ResponseEntity<BookUserDto>  viewReadList(@RequestParam String username, @RequestParam long isbn){
        final var result = apiClientRating.getTheRating(isbn, username);
        logger.info("viewReadList: fetching book using username{} and isbn{}", username,isbn);
        if(result.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.OK).body(bookToRatingDto(apiClientBook.getByIsbn(isbn).getBody(),
                    result.getBody().comments(), result.getBody().rating()));
        }
        logger.info("viewReadList: fetching favourite book using username{} and isbn{} not found", username,isbn);
        throw new BookNotFoundException(
                String.format("No book found in readlist with isbn{} and username{}",isbn, username));
    }




    @DeleteMapping("/deletebook")
    public ResponseEntity<ReadListDto> removeFavourites(@RequestParam long isbn, @RequestParam String username){
        logger.info("Deletebook: delete book from readlist using isbn{} and username{}",isbn,username);
        final var result = readListService.findByIsbnAndUsername(isbn,username);
        if(result.isPresent()) {
            readListService.deletereadlist(result.get().getReadListId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        logger.info("Deletebook:delete  book from readlist using isbn{} and username{} not found",isbn,username);
        throw new BookNotFoundException(
                String.format("No book found in readlist with isbn{} and username{}",isbn, username));
    }

    public ReadList PostToEntity(ReadListDto readListDto) {
        return new ReadList(
                0,
                readListDto.username(),
                readListDto.isbn(),
                LocalDateTime.now(ZoneId.of("Asia/Kolkata"))
        );
    }


    public BookUserDto bookToRatingDto(BookDto bookDto, String comments, int rating){
        return new BookUserDto(
                bookDto.isbn(),
                bookDto.title(),
                bookDto.author(),
                bookDto.summary(),
                bookDto.language(),
                bookDto.pageCount(),
                bookDto.publishYear(),
                bookDto.imageUrl(),
                comments,
                rating
        );
    }

}


















