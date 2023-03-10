package ru.practicum.users.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    @NotNull(message = "Имя пользователя не может быть пустым")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String name;
    @Email(message = "Email не может быть пустым")
    @NotNull(message = "Email не может быть пустым")
    private String email;
}
