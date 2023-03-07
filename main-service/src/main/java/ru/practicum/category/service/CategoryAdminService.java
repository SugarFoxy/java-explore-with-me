package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

public interface CategoryAdminService {
    CategoryDto createCategory(NewCategoryDto categoryDto);

    void deleteCategory(Long id);

    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);
}
