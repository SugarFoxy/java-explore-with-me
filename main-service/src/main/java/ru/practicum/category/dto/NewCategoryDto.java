package ru.practicum.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
public class NewCategoryDto {
    @NotNull
    @NotBlank
    private String name;
}
