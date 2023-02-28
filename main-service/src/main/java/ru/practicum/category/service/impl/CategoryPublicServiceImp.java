package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.service.CategoryPublicService;
import ru.practicum.category.storage.CategoryRepository;
import ru.practicum.util.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.category.mapper.CategoryMapper.toCategoryDto;

@Service
@RequiredArgsConstructor
public class CategoryPublicServiceImp implements CategoryPublicService {
    CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from, size)).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Long id) {
        return toCategoryDto(categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория отсутствует!")));
    }
}
