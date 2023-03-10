package ru.practicum.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryPublicService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/categories")
@CustomExceptionHandler
@Slf4j
public class ControllerPublicCategory {
    private final CategoryPublicService categoryService;

    @Autowired
    public ControllerPublicCategory(CategoryPublicService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public List<CategoryDto> findCategories(
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получен запрос получение категорий");
        return categoryService.getAllCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto findCategoryById(@NotNull @PathVariable Long catId) {
        log.info("Получен запрос на получение информации о категории по ее индетификатору");
        return categoryService.getCategoryById(catId);
    }

}
