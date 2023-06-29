package com.ust.book.service;

import com.ust.book.domain.ReadList;
import com.ust.book.repository.ReadListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ReadListServiceImpl implements  ReadListService {

    @Autowired
    ReadListRepository readListRepository;
    @Override
    public Optional<ReadList> findByIsbn(long isbn) {
        return readListRepository.findByIsbn(isbn);
    }

    @Override
    public ReadList addreadlist(ReadList readList) {
        return readListRepository.save(readList);
    }

    @Override
    public Optional<ReadList> findByIsbnAndUsername(long isbn, String username) {
        return readListRepository.findByIsbnAndUsername(isbn,username);
    }

    @Override
    public Optional<ReadList> findByUsername(String username) {
        return readListRepository.findByUsername(username);
    }

    @Override
    public void deletereadlist(long readListId) {
        readListRepository.deleteById(readListId);
    }

}


