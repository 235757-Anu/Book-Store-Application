package com.ust.book.service;

import com.ust.book.dto.BookDto;
import com.ust.book.dto.ToListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "book-service",url = "http://localhost:8082/api/v1/user/books")

public interface ApiClientBook {
    @PostMapping("/isbn")
    public ResponseEntity<ToListDto> getAllByIsbn(@RequestBody List<Long> isbns);

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getByIsbn(@PathVariable long isbn);
}