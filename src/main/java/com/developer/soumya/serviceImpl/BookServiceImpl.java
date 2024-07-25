package com.developer.soumya.serviceImpl;

import com.developer.soumya.dto.requestDto.BookRequestDto;
import com.developer.soumya.dto.responseDto.BookResponseDto;
import com.developer.soumya.mapper.MapperClass;
import com.developer.soumya.model.Author;
import com.developer.soumya.model.Book;
import com.developer.soumya.model.Category;
import com.developer.soumya.repository.BookRepository;
import com.developer.soumya.service.AuthorService;
import com.developer.soumya.service.BookService;
import com.developer.soumya.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Transactional
    @Override
    public BookResponseDto addBook(BookRequestDto bookRequestDto) {
        Book book = new Book();
        book.setName(bookRequestDto.getName());
        if (bookRequestDto.getAuthorIds().isEmpty()) {
            throw new IllegalArgumentException("you need atLeast on author");
        } else {
            List<Author> authors = new ArrayList<>();
            for (Long authorId : bookRequestDto.getAuthorIds()) {
                Author author = authorService.getAuthor(authorId);
                authors.add(author);
            }
            book.setAuthors(authors);
        }
        if (bookRequestDto.getCategoryId() == null) {
            throw new IllegalArgumentException("book atLeast on category");
        }
        Category category = categoryService.getCategory(bookRequestDto.getCategoryId());
        book.setCategory(category);
        Book book1 = bookRepository.save(book);
        return MapperClass.bookToBookResponseDto(book1);
    }

    @Override
    public BookResponseDto getBookById(Long bookId) {
        Book book = getBook(bookId);
        return MapperClass.bookToBookResponseDto(book);
    }

    @Override
    public Book getBook(Long bookId) {
        return bookRepository
                .findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("cannot fine book with id: " + bookId));
    }

    @Override
    public List<BookResponseDto> getBooks() {
        List<Book> books = StreamSupport
                .stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return MapperClass.booksToBookResponseDtos(books);
    }

    @Override
    public BookResponseDto deleteBook(Long bookId) {
        Book book = getBook(bookId);
        bookRepository.delete(book);
        return MapperClass.bookToBookResponseDto(book);
    }

    @Transactional
    @Override
    public BookResponseDto editBook(Long bookId, BookRequestDto bookRequestDto) {
        Book bookToEdit = getBook(bookId);
        bookToEdit.setName(bookRequestDto.getName());
        if (!bookRequestDto.getAuthorIds().isEmpty()) {
            List<Author> authors = new ArrayList<>();
            for (Long authorId : bookRequestDto.getAuthorIds()) {
                Author author = authorService.getAuthor(authorId);
                authors.add(author);
            }
            bookToEdit.setAuthors(authors);
        }
        if (bookRequestDto.getCategoryId() != null) {
            Category category = categoryService.getCategory(bookRequestDto.getCategoryId());
            bookToEdit.setCategory(category);
        }
        return MapperClass.bookToBookResponseDto(bookToEdit);
    }

    @Override
    public BookResponseDto addAuthorToBook(Long bookId, Long authorId) {
        Book book = getBook(bookId);
        Author author = authorService.getAuthor(authorId);
        if (author.getBooks().contains(author)) {
            throw new IllegalArgumentException("This author is already assigned to this book");

        }
        book.addAuthor(author);
        author.addBook(book);
        return MapperClass.bookToBookResponseDto(book);
    }

    @Override
    public BookResponseDto deleteAuthorFromBook(Long bookId, Long authorId) {
        Book book = getBook(bookId);
        Author author = authorService.getAuthor(authorId);
        if (!(author.getBooks().contains(book))) {
            throw new IllegalArgumentException("Book does not have this author");
        }
        author.removeBook(book);
        book.deleteAuthor(author);
        return MapperClass.bookToBookResponseDto(book);
    }

    @Override
    public BookResponseDto addCategoryToBook(Long bookId, Long categoryId) {
        Book book = getBook(bookId);
        Category category = categoryService.getCategory(categoryId);
        if (Objects.nonNull(book.getCategory())) {
            throw new IllegalArgumentException("Book already has a category");
        }
        book.setCategory(category);
        category.addBook(book);
        return MapperClass.bookToBookResponseDto(book);
    }

    @Override
    public BookResponseDto removeCategoryFromBook(Long bookId, Long categoryId) {
        Book book = getBook(bookId);
        Category category = categoryService.getCategory(categoryId);
        if (!Objects.nonNull(book.getCategory())) throw new IllegalArgumentException("Book does not have a category to delete");
        book.setCategory(null);
        category.removeBook(book);

        return MapperClass.bookToBookResponseDto(book);
    }
}
