package ru.practicum.events.comments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.events.comments.dto.OutCommentDto;
import ru.practicum.events.comments.mapper.CommentMapper;
import ru.practicum.events.comments.model.Comment;
import ru.practicum.events.comments.rating.storage.RatingRepository;
import ru.practicum.events.comments.storage.CommentRepository;
import ru.practicum.events.event.model.Event;
import ru.practicum.util.RepositoryObjectCreator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class CommentPublicServiceImpl implements CommentPublicService {
    private final CommentRepository commentRepository;
    private final RepositoryObjectCreator objectCreator;
    private final RatingRepository ratingRepository;

    @Autowired
    public CommentPublicServiceImpl(CommentRepository commentRepository,
                                    RepositoryObjectCreator objectCreator,
                                    RatingRepository ratingRepository) {
        this.commentRepository = commentRepository;
        this.objectCreator = objectCreator;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<OutCommentDto> getCommentByEvent(Long eventId, Boolean rating, int from, int size) {
        Event event = objectCreator.getEventById(eventId);
        if (!event.getCommentSwitch()) {
            return new ArrayList<>();
        }
        Sort sort = Sort.by(DESC, "commentTime");
        List<Comment> comments = commentRepository
                .findByEvent(event, PageRequest.of(from, size, sort));
        Stream<OutCommentDto> commentDtoStream = comments
                .stream()
                .map(comment -> CommentMapper
                        .toDto(comment,
                                ratingRepository.countGradeByCommentAndGrade(comment, true)
                                        - ratingRepository.countGradeByCommentAndGrade(comment, false)));
        if (rating) {
            commentDtoStream = commentDtoStream.sorted(Comparator.comparingLong(OutCommentDto::getRating).reversed());
        }
        return commentDtoStream.collect(Collectors.toList());
    }
}
