package com.ust.book.repository;

import com.ust.book.domain.ReadList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ReadListRepository extends JpaRepository<ReadList,Long> {


  

    void deleteByIsbn(long isbn);

    Optional<ReadList> findByIsbn(long isbn);
}