package com.ust.book.service;

import com.ust.book.domain.ReadList;


import java.util.List;
import java.util.Optional;

public interface ReadListService {
    Optional<ReadList> findByIsbn(long isbn);
    ReadList addreadlist(ReadList readList);
    Optional<ReadList> findByIsbnAndUsername(long isbn, String username);
    Optional<ReadList> findByUsername(String username);
    void deletereadlist(long readListId);
}
