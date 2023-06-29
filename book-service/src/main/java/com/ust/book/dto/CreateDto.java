package com.ust.book.dto;

public record CreateDto (long isbn,String title,String author, String[] categories,
                         String summary,String language,int pageCount,int publishYear,
                         String imageUrl,double rating){
}
