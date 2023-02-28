package ru.practicum.users.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class UserCreateDto {
    @NotNull
    @NotBlank
    private String name;
    @Email
    @NotNull
    private String email;
}
