package com.developer.soumya.service;

import com.developer.soumya.dto.requestDto.BookRequestDto;
import com.developer.soumya.dto.responseDto.BookResponseDto;
import com.developer.soumya.model.Book;

import java.util.List;

public interface BookService {

    public BookResponseDto addBook(BookRequestDto bookRequestDto);

    public BookResponseDto getBookById(Long bookId);

    public Book getBook(Long bookId);
    public List<BookResponseDto> getBooks();

    public BookResponseDto deleteBook(Long bookId);

    public BookResponseDto editBook(Long bookId, BookRequestDto bookRequestDto);

    public BookResponseDto addAuthorToBook(Long bookId, Long authorId);

    public BookResponseDto deleteAuthorFromBook(Long bookId, Long authorId);

    public BookResponseDto addCategoryToBook(Long bookId, Long categoryId);

    public BookResponseDto removeCategoryFromBook(Long bookId, Long categoryId);
}
