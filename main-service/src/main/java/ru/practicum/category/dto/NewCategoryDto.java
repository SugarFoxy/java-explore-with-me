package ru.practicum.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    @NotNull(message = "Название категории не может быть пустым")
    @NotBlank(message = "Название категории не может быть пустым")
    private String name;
}
