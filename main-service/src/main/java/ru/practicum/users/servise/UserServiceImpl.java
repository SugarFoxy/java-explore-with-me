package ru.practicum.users.servise;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.users.dto.UserCreateDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.mapper.UserMapper;
import ru.practicum.users.storage.UserRepository;
import ru.practicum.util.exception.ConflictException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserCreateDto dto) {
        try {
            return UserMapper.toDto(userRepository.save(UserMapper.toUser(dto)));
        } catch (Exception e) {
            throw new ConflictException("Пользователь с таким email  уже существует");
        }
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> findUsers(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        if (ids.isEmpty()) {
            return userRepository.findAll(pageable).stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findByIds(ids, pageable).stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
        }
    }
}

