package ru.practicum.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryAdminService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/admin/categories")
@CustomExceptionHandler
@Slf4j
public class ControllerAdminCategory {
    private final CategoryAdminService categoryService;

    @Autowired
    public ControllerAdminCategory(CategoryAdminService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto dto) {
        log.info("Получен запрос на добавление категории name = {}", dto.getName());
        return categoryService.createCategory(dto);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@NotNull @PathVariable Long catId,
                                      @Valid @RequestBody CategoryDto dto) {
        log.info("Получен запрос на изменение категории id = {}", catId);
        return categoryService.updateCategory(catId, dto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@NotNull @PathVariable Long catId) {
        log.info("Получен запрос на удаление категории id = {}", catId);
        categoryService.deleteCategory(catId);
    }
}
