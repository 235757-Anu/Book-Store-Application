package com.ust.book.dto;


public record RatingPostDto(
        long ratingId,
        String username,
        String isbn,
        double rating,
        String review) {
    @Override
    public long ratingId() {
        return ratingId;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String isbn() {
        return isbn;
    }

    @Override
    public double rating() {
        return rating;
    }

    @Override
    public String review() {
        return review;
    }
}