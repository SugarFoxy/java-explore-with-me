package ru.practicum.category.controller;

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
public class ControllerPublicCategory {
    private final CategoryPublicService categoryService;

    @Autowired
    public ControllerPublicCategory(CategoryPublicService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public List<CategoryDto> findCategories(
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return categoryService.getAllCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto findCategoryById(@NotNull @PathVariable Long catId) {
        return categoryService.getCategoryById(catId);
    }

}
