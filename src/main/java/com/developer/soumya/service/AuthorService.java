package com.developer.soumya.service;

import com.developer.soumya.dto.requestDto.AuthorRequestDto;
import com.developer.soumya.dto.responseDto.AuthorResponseDto;
import com.developer.soumya.model.Author;

import java.util.List;

public interface AuthorService {
    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto);
    public List<AuthorResponseDto> getAuthors();
    public AuthorResponseDto getAuthorById(Long authorId);
    public Author getAuthor(Long authorId);
    public AuthorResponseDto deleteAuthor(Long authorId);
    public AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto);
    public AuthorResponseDto addZipcodeToAuthor(Long authorId, Long zipcodeId);
    public AuthorResponseDto deleteZipcodeFromAuthor(Long authorId);

}
