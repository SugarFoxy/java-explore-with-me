package ru.practicum.category.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryAdminService;
import ru.practicum.category.storage.CategoryRepository;
import ru.practicum.util.RepositoryObjectCreator;
import ru.practicum.util.exception.ConflictException;

import static ru.practicum.category.mapper.CategoryMapper.toCategory;
import static ru.practicum.category.mapper.CategoryMapper.toCategoryDto;

@Service
public class CategoryAdminServiceImp implements CategoryAdminService {
    private final CategoryRepository categoryRepository;
    private final RepositoryObjectCreator objectCreator;

    @Autowired
    public CategoryAdminServiceImp(CategoryRepository categoryRepository,
                                   RepositoryObjectCreator checkExistence) {
        this.categoryRepository = categoryRepository;
        this.objectCreator = checkExistence;
    }

    public CategoryDto createCategory(NewCategoryDto categoryDto) {
        try {
            return toCategoryDto(categoryRepository.save(toCategory(categoryDto)));
        } catch (Exception e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void deleteCategory(Long id) {
        if (objectCreator.isRelatedCategory(id)) {
            throw new ConflictException("Невозможно удалить! Категория завязана на событие!");
        }
        categoryRepository.deleteById(id);
    }

    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        objectCreator.getCategoryById(catId);
        categoryDto.setId(catId);
        try {
            return toCategoryDto(categoryRepository.save(toCategory(categoryDto)));
        } catch (Exception e) {
            throw new ConflictException("Ктегория c таким названием уже существует");
        }
    }
}
