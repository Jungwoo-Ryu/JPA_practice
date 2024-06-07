package com.ohgiraffers.mapping.section02.embedded;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Stream;

@Service
public class BookRegistService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void registBook(BookRegistDTO newBook) {
        Book book = new Book(
                newBook.getBookTitle(),
                newBook.getAuthor(),
                newBook.getPublisher(),
                newBook.getPublishedDate(),
                new Price(
                        newBook.getRegularPrice(),
                        newBook.getDiscountRate()
                )
        );
    }
}
