package com.ohgiraffers.mapping.section02.embedded;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookNo;

    private String bookTitle;

    private String author;

    private String publisher;

    private LocalDate publishedDate;



    @Embedded
    private Price price;



    protected Book() {}

    public Book(String bookTitle, String author, String publisher, LocalDate publishedDate, Price price) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.price = price;
    }
}
