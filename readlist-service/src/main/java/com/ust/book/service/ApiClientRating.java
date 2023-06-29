package com.ust.book.service;

import com.ust.book.dto.RatingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "rating-service",url = "http://localhost:8300/api/v1/rating")
public interface ApiClientRating {
    @GetMapping
    public ResponseEntity<RatingResponseDto> getTheRating(@RequestParam long isbn, String username);
}