package com.developer.soumya.serviceImpl;

import com.developer.soumya.dto.requestDto.CategoryRequestDto;
import com.developer.soumya.dto.responseDto.CategoryResponseDto;
import com.developer.soumya.mapper.MapperClass;
import com.developer.soumya.model.Category;
import com.developer.soumya.repository.CategoryRepository;
import com.developer.soumya.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category getCategory(Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not find category with is " + categoryId));

    }

    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        categoryRepository.save(category);
        return MapperClass.categoryToCategoryResponseDto(category);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {
        Category category = getCategory(categoryId);
        return MapperClass.categoryToCategoryResponseDto(category);
    }

    @Override
    public List<CategoryResponseDto> getCategories() {
        List<Category> categories = StreamSupport
                .stream(categoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return MapperClass.categoriesToCategoryResponseDtos(categories);
    }

    @Override
    public CategoryResponseDto deleteCategory(Long categoryId) {
        Category category = getCategory(categoryId);
        categoryRepository.delete(category);
        return MapperClass.categoryToCategoryResponseDto(category);
    }

    @Override
    public CategoryResponseDto editCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {
        Category categoryToEdit = getCategory(categoryId);
        categoryToEdit.setName(categoryToEdit.getName());
        return MapperClass.categoryToCategoryResponseDto(categoryToEdit);
    }
}
