package ru.practicum.events.compilation.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}
