package com.ust.book.service;

import com.ust.book.domain.Rating;
import com.ust.book.dto.BookDto;
import com.ust.book.dto.RatingPostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface RatingService {

    BookDto getRatingByIsbnAndUsername(String isbn, String username);

    BookDto getRatingAndReviewsByIsbn(String isbn);

    void rateBook(RatingPostDto ratingPostDto);

    void updateRatingAndReview(String isbn, RatingPostDto ratingPostDto);
}
