package ru.practicum.events.event.util;

import ru.practicum.util.exception.ConflictException;

import java.time.LocalDateTime;

public class CheckTime {
    public static void checkTemporaryValidation(LocalDateTime checked, LocalDateTime original, Integer difference){
        if(checked.isBefore(original.minusHours(difference))) {
            throw new ConflictException("Нельзя изменить событие. Дата начала изменяемого события должна быть не " +
                    "ранее чем за час от даты публикации");
        }
    }

}
