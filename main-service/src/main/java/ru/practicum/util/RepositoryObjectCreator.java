package ru.practicum.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.category.storage.CategoryRepository;
import ru.practicum.events.comments.model.Comment;
import ru.practicum.events.comments.storage.CommentRepository;
import ru.practicum.events.compilation.model.Compilation;
import ru.practicum.events.compilation.storage.CompilationRepository;
import ru.practicum.events.event.model.Event;
import ru.practicum.events.event.storage.EventRepository;
import ru.practicum.events.request.model.Request;
import ru.practicum.events.request.storage.RequestRepository;
import ru.practicum.users.model.User;
import ru.practicum.users.storage.UserRepository;
import ru.practicum.util.exception.NotFoundException;

@Service
public class RepositoryObjectCreator {
   private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final CompilationRepository compilationRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public RepositoryObjectCreator(UserRepository userRepository,
                                   CategoryRepository categoryRepository,
                                   EventRepository eventRepository,
                                   RequestRepository requestRepository,
                                   CompilationRepository compilationRepository,
                                   CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
        this.requestRepository = requestRepository;
        this.compilationRepository = compilationRepository;
        this.commentRepository = commentRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(getMassage("Пользователь", id)));
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(getMassage("Событие", id)));
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(getMassage("Категория", id)));
    }

    public Request getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(getMassage("Запрос", id)));
    }

    public Compilation getCompilationById(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(getMassage("Подборка", compId)));
    }

    public Comment getCommentById(Long commId) {
        return commentRepository.findById(commId)
                .orElseThrow(() -> new NotFoundException(getMassage("Комментарий", commId)));
    }
    public Boolean isRelatedCategory(Long id) {
        return eventRepository.existsByCategory(getCategoryById(id));
    }

    private String getMassage(String model, Long id) {
        return String.format("%s id = %d отсутствует", model, id);
    }


}
