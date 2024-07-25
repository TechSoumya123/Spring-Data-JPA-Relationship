package com.developer.soumya.serviceImpl;

import com.developer.soumya.dto.requestDto.AuthorRequestDto;
import com.developer.soumya.dto.responseDto.AuthorResponseDto;
import com.developer.soumya.mapper.MapperClass;
import com.developer.soumya.model.Author;
import com.developer.soumya.model.ZipCode;
import com.developer.soumya.repository.AuthorRepository;
import com.developer.soumya.service.AuthorService;
import com.developer.soumya.service.ZipCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final ZipCodeService zipCodeService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ZipCodeService zipCodeService) {
        this.authorRepository = authorRepository;
        this.zipCodeService = zipCodeService;
    }

    @Transactional
    @Override
    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto) {
        Author author = new Author();
        author.setName(authorRequestDto.getName());
        if (authorRequestDto.getZipcodeId() == null) {
            throw new IllegalArgumentException("author need a zipcode");
        }
        ZipCode zipCode = zipCodeService.getZipCodeById(authorRequestDto.getZipcodeId());
        author.setZipcode(zipCode);
        authorRepository.save(author);
        return MapperClass.authorToAuthorResponseDto(author);
    }

    @Override
    public List<AuthorResponseDto> getAuthors() {

        List<Author> authors = new ArrayList<>(authorRepository.findAll());
        return MapperClass.authorsToAuthorResponseDtos(authors);
    }

    @Override
    public AuthorResponseDto getAuthorById(Long authorId) {
        return MapperClass.authorToAuthorResponseDto(getAuthor(authorId));
    }

    @Override
    public Author getAuthor(Long authorId) {
        return authorRepository
                .findById(authorId)
                .orElseThrow(() ->
                        new IllegalArgumentException("author with id: " + authorId + "could not be found"));
    }

    @Override
    public AuthorResponseDto deleteAuthor(Long authorId) {
        Author author = getAuthor(authorId);
        authorRepository.delete(author);
        return MapperClass.authorToAuthorResponseDto(author);
    }

    @Transactional
    @Override
    public AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto) {
        Author authorToEdit = getAuthor(authorId);
        authorToEdit.setName(authorRequestDto.getName());
        if (authorRequestDto.getZipcodeId() != null) {
            ZipCode zipCode = zipCodeService.getZipCodeById(authorRequestDto.getZipcodeId());
            authorToEdit.setZipcode(zipCode);
        }
        return MapperClass.authorToAuthorResponseDto(authorToEdit);
    }

    @Transactional
    @Override
    public AuthorResponseDto addZipcodeToAuthor(Long authorId, Long zipCodeId) {
        Author author = getAuthor(authorId);
        ZipCode zipCode = zipCodeService.getZipCodeById(zipCodeId);
        if (Objects.nonNull(author.getZipcode())) {
            throw new RuntimeException("author already has a zipcode");
        }
        author.setZipcode(zipCode);
        return MapperClass.authorToAuthorResponseDto(author);
    }


    @Override
    public AuthorResponseDto deleteZipcodeFromAuthor(Long authorId) {
        Author author = getAuthor(authorId);
        author.setZipcode(null);
        return MapperClass.authorToAuthorResponseDto(author);
    }
}
