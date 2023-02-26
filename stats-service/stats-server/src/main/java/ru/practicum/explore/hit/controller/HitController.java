package ru.practicum.explore.hit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explore.dto.HitDto;
import ru.practicum.explore.hit.service.HitService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

@RestController
@CustomExceptionHandler
@RequestMapping(path = "/hit")
@Slf4j
public class HitController {
    private final HitService service;

    @Autowired
    public HitController(@Qualifier("hitServiceImp") HitService hitService) {
        this.service = hitService;
    }

    @PostMapping
    public void saveRequest(@Validated @RequestBody HitDto hit) {
        service.saveRequest(hit);
        log.info("{} {} сохранен", hit.getApp(), hit.getUri());
    }
}
