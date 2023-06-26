package com.ust.book.dto;



public record RatingResponseDto (
        String username,
        String isbn,
        double rating,
        String review) {

}
