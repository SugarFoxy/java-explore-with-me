package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryAdminService;
import ru.practicum.category.storage.CategoryRepository;
import ru.practicum.util.exception.ConflictException;
import ru.practicum.util.exception.NotFoundException;

import static ru.practicum.category.mapper.CategoryMapper.toCategory;
import static ru.practicum.category.mapper.CategoryMapper.toCategoryDto;

@Service
@RequiredArgsConstructor
public class CategoryAdminServiceImp implements CategoryAdminService {
    CategoryRepository categoryRepository;

    public CategoryDto createCategory(NewCategoryDto categoryDto) {
        try {
        return toCategoryDto(categoryRepository.save(toCategory(categoryDto)));
        } catch (Exception e) {
            throw new ConflictException("Ктегория c таким названием уже существует");
        }
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto) {
        checkAvailability(categoryDto.getId());
        try {
        return toCategoryDto(categoryRepository.save(toCategory(categoryDto)));
        } catch (Exception e) {
            throw new ConflictException("Ктегория c таким названием уже существует");
        }
    }

    private void checkAvailability(Long id){
        categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Категория отсутствует!"));
    }
}
