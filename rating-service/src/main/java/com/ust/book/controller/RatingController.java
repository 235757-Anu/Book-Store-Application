package com.ust.book.controller;

import com.ust.book.dto.BookDto;
import com.ust.book.dto.RatingPostDto;
import com.ust.book.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/ratings")
    public class RatingController {

        private RatingService ratingService;

        public RatingController(RatingService ratingService) {
            this.ratingService = ratingService;
        }

        @GetMapping("/{isbn}/{username}")
        public ResponseEntity<BookDto> getRatingByIsbnAndUsername(@PathVariable String isbn, @PathVariable String username) {
            BookDto bookDto = ratingService.getRatingByIsbnAndUsername(isbn, username);
            if (bookDto != null) {
                return ResponseEntity.ok(bookDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @GetMapping("/{isbn}")
        public ResponseEntity<BookDto> getRatingAndReviewsByIsbn(@PathVariable String isbn) {
            BookDto bookDto = ratingService.getRatingAndReviewsByIsbn(isbn);
            if (bookDto != null) {
                return ResponseEntity.ok(bookDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping
        public ResponseEntity<Void> rateBook(@RequestBody RatingPostDto ratingPostDto) {
            ratingService.rateBook(ratingPostDto);
            return ResponseEntity.ok().build();
        }

        @PutMapping("/{isbn}")
        public ResponseEntity<Void> updateRatingAndReview(@PathVariable String isbn, @RequestBody RatingPostDto ratingPostDto) {
            ratingService.updateRatingAndReview(isbn, ratingPostDto);
            return ResponseEntity.ok().build();
        }
}