package ru.practicum.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserShortDto  {
    private long id;
    private String name;
}
