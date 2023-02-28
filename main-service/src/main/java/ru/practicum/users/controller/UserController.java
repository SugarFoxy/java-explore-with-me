package ru.practicum.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.users.dto.UserCreateDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.servise.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping()
    public UserDto createUser(@Valid @RequestBody UserCreateDto dto) {
        return service.createUser(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        service.deleteUser(id);
    }

    @GetMapping()
    public List<UserDto> findUsers(@RequestParam List<Long> ids,
                                   @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                   @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.findUsers(ids, from, size);
    }
}