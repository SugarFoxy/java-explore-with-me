package ru.practicum.explore.hit.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.hit.model.Hit;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
}
