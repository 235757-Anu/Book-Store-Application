package com.ust.book.service;

import com.ust.book.domain.Rating;
import com.ust.book.dto.BookDto;
import com.ust.book.dto.RatingPostDto;
import com.ust.book.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public BookDto getRatingByIsbnAndUsername(String isbn, String username) {
        OptionalDouble averageRating = ratingRepository.findByIsbnAndUsername(isbn, username)
                .stream().mapToDouble(Rating::getRating)
                .average();

        if (averageRating.isPresent()) {
            double rating = averageRating.getAsDouble();
            List<Rating> ratings = ratingRepository.findByIsbn(isbn);
            return createBookDto(rating, ratings);
        }

        return null;
    }

    @Override
    public BookDto getRatingAndReviewsByIsbn(String isbn) {
        List<Rating> ratings = ratingRepository.findByIsbn(isbn);
        if (!ratings.isEmpty()) {
            double averageRating = ratings.stream()
                    .mapToDouble(Rating::getRating)
                    .average()
                    .orElse(0.0);
            return createBookDto(averageRating, ratings);
        }
        return null;
    }

    @Override
    public void rateBook(RatingPostDto ratingPostDto) {
        Rating rating = new Rating();
        rating.setUsername(ratingPostDto.username());
        rating.setIsbn(ratingPostDto.isbn());
        rating.setRating(ratingPostDto.rating());
        rating.setReview(ratingPostDto.review());
        ratingRepository.save(rating);
    }

    @Override
    public void updateRatingAndReview(String isbn, RatingPostDto ratingPostDto) {
        Optional<Rating> optionalRating = ratingRepository.findByIsbnAndUsername(isbn, ratingPostDto.username());
        if (optionalRating.isPresent()) {
            Rating rating = optionalRating.get();
            rating.setRating(ratingPostDto.rating());
            rating.setReview(ratingPostDto.review());
            ratingRepository.save(rating);
        }
    }

    private BookDto createBookDto(double rating, List<Rating> ratings) {
        String isbn = ratings.get(0).getIsbn();
        String username = ratings.get(0).getUsername();
        String review = ratings.get(0).getReview();
        return new BookDto(0, username, isbn, rating, review);
    }
}
