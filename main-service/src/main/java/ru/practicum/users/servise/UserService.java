package ru.practicum.users.servise;

import ru.practicum.users.dto.UserCreateDto;
import ru.practicum.users.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserCreateDto dto);

    void deleteUser(long id);

    List<UserDto> findUsers(List<Long> ids, Integer from, Integer size);
}
