package ru.practicum.events.comments.rating.model;

import lombok.*;
import ru.practicum.events.comments.model.Comment;
import ru.practicum.users.model.User;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User rater;
    @ManyToOne()
    @JoinColumn(name = "comment_id")
    private Comment comment;
    private Boolean grade;
}
