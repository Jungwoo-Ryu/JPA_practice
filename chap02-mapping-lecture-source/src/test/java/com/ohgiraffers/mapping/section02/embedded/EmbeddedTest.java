package com.ohgiraffers.mapping.section02.embedded;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.Stream;

@SpringBootTest
public class EmbeddedTest {

    @Autowired
    private BookRegistService bookRegistService;

    private static Stream<Arguments> getBook(){
        return Stream.of(
                Arguments.of(
                        "JPA",
                        "조평훈",
                        "에이콘",
                        LocalDate.now(),
                        43000,
                        0.1
                )
        );
    }

    @DisplayName("임베디드 테스트")
    @ParameterizedTest
    @MethodSource("getBook")
    void testCreateEmbeddedPriceOfBook(String bookTitle, String author, String publisher,
                                       LocalDate publishedDate, int regularPrice, double discountRate){
        BookRegistDTO newBook = new BookRegistDTO(
                bookTitle,
                author,
                publisher,
                publishedDate,
                regularPrice,
                discountRate
        );

        Assertions.assertDoesNotThrow(
                () -> bookRegistService.registBook(newBook)
        );
    }
}
