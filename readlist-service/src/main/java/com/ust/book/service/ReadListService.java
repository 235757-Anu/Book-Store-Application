package com.ust.book.service;

import com.ust.book.domain.ReadList;


import java.util.List;
import java.util.Optional;

public interface ReadListService {


    ReadList addToReadList(ReadList readList);


    List<ReadList> getAllReadLists();



    void deleteReadListByIsbn(long isbn);

    Optional<ReadList> getReadListByIsbn(long isbn);
}
