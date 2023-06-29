package com.ust.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookId;
    private long isbn;
    private String title;
    private String author;

    @ElementCollection
    @OrderColumn(name = "category_order")
    private String[] categories;

    @Column(length = 3000)
    private String summary;
    private String language;
    private int pageCount;
    private int publishYear;
    private String imageUrl;
    private double rating;
}
