package com.ust.book.controller;

import com.ust.book.domain.ReadList;

import com.ust.book.service.ReadListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/readlist")
@CrossOrigin("*")
public class ReadListController {
    @Autowired
    private ReadListService readListService;

    @PostMapping("/addtoreadlist")
    public ResponseEntity<ReadList> addToReadList(@RequestBody ReadList readList) {
        ReadList addedReadList = readListService.addToReadList(readList);
        return ResponseEntity.ok().body(addedReadList);
    }

    @GetMapping("/getallreadlist")
    public ResponseEntity<List<ReadList>> getAllReadLists() {
        List<ReadList> readLists = readListService.getAllReadLists();
        return new ResponseEntity<>(readLists, HttpStatus.OK);
    }

    @GetMapping("/getreadlist/{isbn}")

    public ResponseEntity<ReadList> getReadListById(@PathVariable long isbn) {
        Optional<ReadList> readList = readListService.getReadListByIsbn(isbn);
        return readList.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deletereadlist/{isbn}")
    public ResponseEntity<Void> deleteReadListById(@PathVariable long isbn) {
        readListService.deleteReadListByIsbn(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}


















