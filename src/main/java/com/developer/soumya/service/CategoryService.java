package com.developer.soumya.service;

import com.developer.soumya.dto.requestDto.CategoryRequestDto;
import com.developer.soumya.dto.responseDto.CategoryResponseDto;
import com.developer.soumya.model.Category;

import java.util.List;

public interface CategoryService {

    public Category getCategory(Long categoryId);

    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto);

    public CategoryResponseDto getCategoryById(Long categoryId);

    public List<CategoryResponseDto> getCategories();

    public CategoryResponseDto deleteCategory(Long categoryId);

    public CategoryResponseDto editCategory(Long categoryId, CategoryRequestDto categoryRequestDto);


}
