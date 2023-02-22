package ru.practicum.explore.hit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explore.dto.HitDto;
import ru.practicum.explore.hit.mapper.HitMapper;
import ru.practicum.explore.hit.storage.HitRepository;

@Service
public class HitServiceImp implements HitService {
    private final HitRepository repository;

    @Autowired
    public HitServiceImp(HitRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveRequest(HitDto hit) {
        repository.save(HitMapper.toHit(hit));
    }
}
