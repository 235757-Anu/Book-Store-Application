package com.ust.book.service;

import com.ust.book.domain.ReadList;


import com.ust.book.repository.ReadListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadListServiceImpl implements  ReadListService {

    @Autowired
    private ReadListRepository readListRepository;


    @Override
    public ReadList addToReadList(ReadList readList) {

        return readListRepository.save(readList);
    }

    @Override
    public List<ReadList> getAllReadLists() {

        return readListRepository.findAll();
    }



    @Override
    public void deleteReadListByIsbn(long isbn) {

        readListRepository.deleteByIsbn(isbn);
    }

    @Override
    public Optional<ReadList> getReadListByIsbn(long isbn) {

        return readListRepository.findByIsbn(isbn);
    }
}


