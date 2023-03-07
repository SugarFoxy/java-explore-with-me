package ru.practicum.category.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.service.CategoryPublicService;
import ru.practicum.category.storage.CategoryRepository;
import ru.practicum.util.RepositoryObjectCreator;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.category.mapper.CategoryMapper.toCategoryDto;

@Service
public class CategoryPublicServiceImp implements CategoryPublicService {
    private final CategoryRepository categoryRepository;
    private final RepositoryObjectCreator checkExistence;

    @Autowired
    public CategoryPublicServiceImp(CategoryRepository categoryRepository, RepositoryObjectCreator checkExistence) {
        this.categoryRepository = categoryRepository;
        this.checkExistence = checkExistence;
    }

    public List<CategoryDto> getAllCategories(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from, size)).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Long id) {
        return toCategoryDto(checkExistence.getCategoryById(id));
    }
}
