package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryAdminService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class ControllerAdminCategory {
    private final CategoryAdminService categoryService;

    @PostMapping()
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto dto) {
        return categoryService.createCategory(dto);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto dto) {
        return categoryService.updateCategory(dto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@NotNull @PathVariable Long catId) {
        categoryService.deleteCategory(catId);
    }
}
