package ru.practicum.events.comments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.events.comments.dto.CommentDto;
import ru.practicum.events.comments.mapper.CommentMapper;
import ru.practicum.events.comments.rating.storage.RatingRepository;
import ru.practicum.events.comments.storage.CommentRepository;
import ru.practicum.util.RepositoryObjectCreator;

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
    public List<CommentDto> getCommentByEvent(Long eventId, Boolean rating, int from, int size) {
        Sort sort1 = Sort.by(DESC, "comment_time");
        Stream<CommentDto> commentDtoStream =commentRepository
                .findCommentsByEvent(objectCreator.getEventById(eventId), PageRequest.of(from, size, sort1))
                .stream()
                .map(comment -> CommentMapper
                        .toDto(comment,
                                ratingRepository.countGradeByCommentAndGrade(comment,true)
                                        - ratingRepository.countGradeByCommentAndGrade(comment,false)));
        if (rating){
           commentDtoStream = commentDtoStream.sorted(Comparator.comparingLong(CommentDto::getRating).reversed());
        }
        return commentDtoStream.collect(Collectors.toList());
    }
}
